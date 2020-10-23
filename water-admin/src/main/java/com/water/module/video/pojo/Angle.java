package com.water.module.video.pojo;

/**
 * @author：my
 * @date：2019/12/11 17:58
 * @describe：
 */
public class Angle {
    private Integer id;
    private float x;
    private float y;
    private float z;

    @Override
    public String toString() {
        return "Angle{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Angle(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Angle() {
    }
}
