package com.rts.game.pathfinding;

import com.rts.game.entities.Entity;

public class Node {
    int x;

    public Entity standing;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getCenterX() {
        return x + 0.5f;
    }

    public float getCenterY() {
        return y + 0.5f;
    }


    public void setX(int x) {
        this.x = x;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isPass() {
        if (standing != null) {
            return false;
        }


        return pass;
    }

    public void setPassable(boolean pass) {
        this.pass = pass;
    }

    int y;
    float g;
    float f;  //g = from start; f = distance from start and end
    private boolean pass;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.pass = true;
    }

    public void update(float g, float h, Node parent) {
        this.parent = parent;
        this.g = g;
        f = g + h;
    }

    public void debug() {
        System.out.println("Node: " + x + ", " + y + ", " + (pass ? "Walkable" : "Not Walkable") + ", " + standing);
    }

}
