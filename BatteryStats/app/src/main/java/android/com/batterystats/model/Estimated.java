package android.com.batterystats.model;

import android.com.batterystats.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nguyenbinh on 01/05/2017.
 */

public class Estimated implements Serializable{
    public class Entity implements Serializable{
        public String name;
        public float use;
    }

    private ArrayList<Entity> listEntity;
    private String description;

    public Estimated(Context context, File estimateFileData) {
        listEntity = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(estimateFileData)));
            reader.readLine();
            String[] desArgs = reader.readLine().toLowerCase().split(",");
            desArgs[1] = desArgs[1].replace("computed drain", context.getString(R.string.compute_drain));
            desArgs[2] = desArgs[2].replace("actual drain", context.getString(R.string.actual_drain));
            description = desArgs[1] + " mAh\n" + desArgs[2] + " mAh";
            String currentLine = reader.readLine();
            Entity entitySystem = new Entity();
            entitySystem.name = "System";
            entitySystem.use = 0;
            while (!currentLine.contains("(mAh)")) {
                if (currentLine.toLowerCase().contains("uid")) {
                    String[] arg = currentLine.trim().split(" ");
                    Entity entity = new Entity();
                    entity.name = arg[1].trim();
                    try {
                        entity.use = Float.parseFloat(arg[2].trim());
                    } catch (Exception e) {
                        continue;
                    }
                    if(entity.name.contains(".")) {
                        listEntity.add(entity);
                    }else {
                        entitySystem.use += entity.use;
                    }
                } else {
                    String[] arg = currentLine.trim().split(":");
                    Entity entity = new Entity();
                    entity.name = arg[0].trim();
                    try {
                        entity.use = Float.parseFloat(arg[1]);
                    } catch (Exception e) {
                        continue;
                    }
                    listEntity.add(entity);
                }
                currentLine = reader.readLine();
            }
            listEntity.add(entitySystem);
        } catch (Exception e) {
            e.printStackTrace();
            description = context.getString(R.string.unknown);
            listEntity = new ArrayList<>();
        }
        if(listEntity.size() > 0){
            Collections.sort(listEntity, new Comparator<Entity>() {
                @Override
                public int compare(Entity entity1, Entity entity2) {
                    return ((int) (entity2.use * 1000)) - ((int) (entity1.use * 1000));
                }
            });
            for(int i = 10; i < listEntity.size(); i++){
                listEntity.get(9).name = "Other";
                listEntity.get(9).use += listEntity.get(i).use;
            }
            while (listEntity.size() > 10){
                listEntity.remove(listEntity.size() - 1);
            }
            Collections.shuffle(listEntity);
        }
    }

    public ArrayList<Entity> getListEntity() {
        return listEntity;
    }

    public void setListEntity(ArrayList<Entity> listEntity) {
        this.listEntity = listEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
