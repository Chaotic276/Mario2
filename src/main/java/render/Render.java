package render;

import components.SpriteRender;
import jade.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Render {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Render(){
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go){
        SpriteRender spr = go.getComponent(SpriteRender.class);
        if(spr != null){
            add(spr);
        }
    }

    public void add(SpriteRender spr){
        boolean added = false;
        for(RenderBatch batch : batches){
            if(batch.hasRoom()){
                batch.addSprite(spr);
                added = true;
                break;
            }
        }

        if(!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spr);
        }
    }

    public void render(){
        for(RenderBatch batch : batches){
            batch.render();
        }
    }
}
