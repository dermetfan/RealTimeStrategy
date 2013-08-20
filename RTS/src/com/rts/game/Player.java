package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {

    public HashMap<Integer, Entity> ownedEntities = new HashMap<Integer, Entity>(256);
    public ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);
    public Entity currentSelect;
    public String name;
    boolean runningSelection = false;
    float[] selectionStart = new float[]{0, 0};
    float[] selectionEnd = new float[]{0, 0};
    BoundingShape selectBounds;
    Camera cam;

    public void create() {
        selectBounds = new BoundingShape(1, 1, 1, 1);
        cam = new Camera();
    }

    public void update(float delta, EntityManager ents) {
        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT)) || runningSelection) {
            selection(ents);
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            moveSelectedUnits();
        }

    }

    private void moveSelectedUnits() {
        for (Entity e : currentSelection) {
            e.setX(cam.getRealWorldPosition()[0]);
            e.setY(cam.getRealWorldPosition()[1]);
        }
    }

    private void selection(EntityManager entities) {
        float pos[] = cam.getRealWorldPosition();

        if (!runningSelection) {
            selectionStart = pos;
        }

        runningSelection = true;

        selectionEnd = pos;

        selectBounds = new BoundingShape(selectionStart, selectionEnd);


        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            currentSelection.clear();
            runningSelection = false;
            currentSelection.addAll(selectBounds.findInContainer(entities));
            System.out.println(currentSelection.size());
        }

    }

    public void cameraUpdates() {
        cam.update(Gdx.graphics.getDeltaTime());
    }

    public void draw() {
        drawSelectionBox();
        cam.draw();
    }

    private void drawSelectionBox() {

        if (runningSelection) {


            ShapeRenderer box = new ShapeRenderer();
            box.setProjectionMatrix(cam.getOrthographicCamera().combined);

            box.begin(ShapeRenderer.ShapeType.FilledRectangle);

            box.setColor(1, 0, 1, 0.1f);

            float lx = selectionEnd[0];
            float ly = selectionEnd[1];
            if (selectionStart[0] < selectionEnd[0]) {
                lx = selectionStart[0];
            }
            if (selectionStart[1] < selectionEnd[1]) {
                ly = selectionStart[1];
            }

            box.filledRect(lx, ly, selectBounds.width, selectBounds.height);
            box.end();
        }
    }


}
