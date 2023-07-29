package render;

import components.SpriteRender;
import jade.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Render {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Render() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRender spr = go.getComponent(SpriteRender.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRender sprite) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}