import logging
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS
import json

app = Flask(__name__)
cors = CORS(app)
app.config["CORS_HEADERS"] = "Content-Type"
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///recipes.db"  # SQLite database file
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db = SQLAlchemy(app)


# Recipe model
class Recipe(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), nullable=False)
    description = db.Column(db.String(1000))
    ingredients = db.relationship("Ingredient", backref="recipe", lazy=True)
    instructions = db.relationship("Instruction", backref="recipe", lazy=True)
    imageUrl = db.Column(db.String(255))


# Ingredient model
class Ingredient(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), nullable=False)
    quantity = db.Column(db.Float, nullable=False)
    unit = db.Column(db.String(50))
    recipe_id = db.Column(db.Integer, db.ForeignKey("recipe.id"), nullable=False)


# Instruction model
class Instruction(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    step = db.Column(db.String(1000), nullable=False)
    recipe_id = db.Column(db.Integer, db.ForeignKey("recipe.id"), nullable=False)


# Routes


# Get all recipes with all fields
@app.route("/recipes", methods=["GET"])
def get_recipes():
    recipes = Recipe.query.all()
    count = len(recipes)
    return jsonify(
        {
            "count": count,
            "results": [
                {
                    "id": recipe.id,
                    "name": recipe.name,
                    "description": recipe.description,
                    "ingredients": [
                        {
                            "name": ingredient.name,
                            "quantity": ingredient.quantity,
                            "unit": ingredient.unit,
                        }
                        for ingredient in recipe.ingredients
                    ],
                    "instructions": [
                        instruction.step for instruction in recipe.instructions
                    ],
                    "imageUrl": recipe.imageUrl,
                }
                for recipe in recipes
            ],
        }
    )


# Get a single recipe
@app.route("/recipes/<int:recipe_id>", methods=["GET"])
def get_recipe(recipe_id):
    recipe = Recipe.query.get_or_404(recipe_id)
    return jsonify(
        {
            "id": recipe.id,
            "name": recipe.name,
            "description": recipe.description,
            "ingredients": [
                {
                    "name": ingredient.name,
                    "quantity": ingredient.quantity,
                    "unit": ingredient.unit,
                }
                for ingredient in recipe.ingredients
            ],
            "instructions": [instruction.step for instruction in recipe.instructions],
            "imageUrl": recipe.imageUrl,
        }
    )


# Create a recipe
@app.route("/recipes", methods=["POST"])
def create_recipe():
    data = request.get_json()
    app.logger.info(data)

    """
    data example
    {'description': 'test test tezt ', 'id': -1, 'imageUrl': 'https://picsum.photos/200/300', 'ingredients': [{'name': 'alma', 'quantity': 10.0, 'unit': 'kg'}, {'name': 'körte', 'quantity': 20.0, 'unit': 'kg'}], 'instructions': ['pucold meg alma', 'pucold meg körte '], 'name': 'test'}
    """

    recipe = Recipe(
        name=data["name"],
        description=data.get("description", ""),
        imageUrl=data.get("imageUrl", ""),
    )
    db.session.add(recipe)

    for ingredient_data in data.get("ingredients", []):
        ingredient = Ingredient(
            name=ingredient_data["name"],
            quantity=ingredient_data["quantity"],
            unit=ingredient_data["unit"],
            recipe=recipe,
        )
        db.session.add(ingredient)

    for instruction_data in data.get("instructions", []):
        instruction = Instruction(step=instruction_data, recipe=recipe)
        db.session.add(instruction)

    db.session.commit()

    # get the id of the newly created recipe
    new_recipe = Recipe.query.order_by(Recipe.id.desc()).first()

    return (
        jsonify({"message": "Recipe created successfully", "recipe_id": new_recipe.id}),
        201,
    )


# Update a recipe
@app.route("/recipes/<int:recipe_id>", methods=["PUT"])
def update_recipe(recipe_id):
    recipe = Recipe.query.get_or_404(recipe_id)
    data = request.get_json()

    recipe.name = data["name"]
    recipe.description = data.get("description", "")
    recipe.imageUrl = data.get("imageUrl", "")

    # Delete existing ingredients and instructions
    Ingredient.query.filter_by(recipe_id=recipe.id).delete()
    Instruction.query.filter_by(recipe_id=recipe.id).delete()

    # Add new ingredients and instructions
    for ingredient_data in data.get("ingredients", []):
        ingredient = Ingredient(
            name=ingredient_data["name"],
            quantity=ingredient_data["quantity"],
            unit=ingredient_data["unit"],
            recipe=recipe,
        )
        db.session.add(ingredient)

    for instruction_data in data.get("instructions", []):
        instruction = Instruction(step=instruction_data, recipe=recipe)
        db.session.add(instruction)

    db.session.commit()
    return jsonify({"message": "Recipe updated successfully"})


# Delete a recipe
@app.route("/recipes/<int:recipe_id>", methods=["DELETE"])
def delete_recipe(recipe_id):
    recipe = Recipe.query.get_or_404(recipe_id)

    # Delete the instructions and ingredients first
    Ingredient.query.filter_by(recipe_id=recipe.id).delete()
    Instruction.query.filter_by(recipe_id=recipe.id).delete()

    # Delete the recipe
    db.session.delete(recipe)
    db.session.commit()
    return jsonify({"message": "Recipe deleted successfully"})


def seed_database():
    with open("seed.json", "r") as file:
        seed_data = json.load(file)

    if len(Recipe.query.all()) > 0:
        return

    for item in seed_data:
        new_recipe = Recipe(
            name=item["name"],
            description=item.get("description", ""),
            imageUrl=item.get("imageUrl", ""),
        )
        db.session.add(new_recipe)

        for ingredient_data in item.get("ingredients", []):
            ingredient = Ingredient(
                name=ingredient_data["name"],
                quantity=ingredient_data["quantity"],
                unit=ingredient_data["unit"],
                recipe=new_recipe,
            )
            db.session.add(ingredient)

        for instruction_data in item.get("instructions", []):
            instruction = Instruction(step=instruction_data, recipe=new_recipe)
            db.session.add(instruction)

    db.session.commit()


if __name__ == "__main__":
    with app.app_context():
        db.create_all()
        seed_database()
    app.run(host="0.0.0.0", port=5000, debug=True)