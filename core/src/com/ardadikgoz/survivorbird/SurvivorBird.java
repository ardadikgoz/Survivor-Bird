package com.ardadikgoz.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture dragon;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float dragonX;
	float dragonY;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.6f;
	float objectHeight;
	float objectWidth;
	float enemyVelocity = 10;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle dragonCircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffset1 = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3 = new float[numberOfEnemies];
	Circle [] enemyCircles1;
	Circle [] enemyCircles2;
	Circle [] enemyCircles3;

	float distance = 0;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		dragon = new Texture("dragon.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		dragonCircle = new Circle();
		enemyCircles1 = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		font2.getData().setScale(6);

		shapeRenderer = new ShapeRenderer();

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();

		dragonX = Gdx.graphics.getWidth()/4;
		dragonY = Gdx.graphics.getHeight()/2;

		objectHeight = Gdx.graphics.getHeight()/10;
		objectWidth = Gdx.graphics.getWidth()/15;

		for (int i = 0; i<numberOfEnemies;i++){

			enemyOffset1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i*distance;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1){

			if (enemyX[scoredEnemy] < dragonX){
				score++;
				if (scoredEnemy < numberOfEnemies-1){
					scoredEnemy++;
				}else {
					scoredEnemy=0;
				}
			}

			if (Gdx.input.justTouched()){
				velocity = -15;
			}

			for (int i = 0; i<numberOfEnemies;i++){

				if (enemyX[i]<-enemy1.getWidth()){

					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffset1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);

				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset1[i],objectWidth,objectHeight);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset2[i],objectWidth,objectHeight);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset3[i],objectWidth,objectHeight);

				enemyCircles1[i] = new Circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset1[i] +objectHeight/2,objectWidth/3);
				enemyCircles2[i] = new Circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset2[i] +objectHeight/2,objectWidth/3);
				enemyCircles3[i] = new Circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset3[i] +objectHeight/2,objectWidth/3);

			}




			if ((dragonY > 0)){
				velocity = velocity + gravity;
				dragonY = dragonY - velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState  == 0) {
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",Gdx.graphics.getWidth()/4-20,Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState = 1;

				dragonY = Gdx.graphics.getHeight()/2;

				for (int i = 0; i<numberOfEnemies;i++){

					enemyOffset1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i*distance;

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;


			}
		}

		batch.draw(dragon,dragonX,dragonY,objectWidth,objectHeight);
		font.draw(batch,"Score: " + score,100,200);
		dragonCircle.set(dragonX + objectWidth/2,dragonY + objectHeight/2,objectWidth/3);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(dragonX + objectWidth/2,dragonY + objectHeight/2,objectWidth/3);



		for (int i = 0 ; i< numberOfEnemies; i++){
			//shapeRenderer.circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset1[i] +objectHeight/2,objectWidth/3);
			//shapeRenderer.circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset2[i] +objectHeight/2,objectWidth/3);
			//shapeRenderer.circle(enemyX[i] + objectWidth/2, Gdx.graphics.getHeight()/2  + enemyOffset3[i] +objectHeight/2,objectWidth/3);

			if (Intersector.overlaps(dragonCircle,enemyCircles1[i]) || Intersector.overlaps(dragonCircle,enemyCircles2[i]) || Intersector.overlaps(dragonCircle,enemyCircles3[i])){
				gameState = 2;
			}

		}

		//shapeRenderer.end();
		batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
