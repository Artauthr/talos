package com.talosvfx.talos.editor.addons.scene.logic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.talosvfx.talos.editor.addons.scene.assets.AssetRepository;
import com.talosvfx.talos.editor.addons.scene.assets.GameAsset;
import com.talosvfx.talos.editor.addons.scene.assets.GameAssetType;
import com.talosvfx.talos.editor.addons.scene.widgets.property.AssetSelectWidget;
import com.talosvfx.talos.editor.widgets.propertyWidgets.*;

import java.util.function.Supplier;

public class SpriteRendererComponent extends RendererComponent implements GameResourceOwner<Texture> {

    public GameAsset<Texture> gameAsset;
    TextureRegion textureRegion;


    public Color color = new Color(Color.WHITE);
    public boolean flipX;
    public boolean flipY;
    public RenderMode renderMode = RenderMode.simple;

    @Override
    public GameAsset<Texture> getGameResource () {
        return gameAsset;
    }

    @Override
    public void setGameAsset (GameAsset<Texture> newGameAsset) {
        if (this.gameAsset != null) {
            //Remove from old game asset, it might be the same, but it may also have changed
            this.gameAsset.listeners.removeValue(gameAssetUpdateListener, true);
        }

        this.gameAsset = newGameAsset;
        this.gameAsset.listeners.add(gameAssetUpdateListener);

        gameAssetUpdateListener.onUpdate();

    }

    public enum RenderMode {
        simple,
        sliced,
        tiled
    }

    @Override
    public Class<? extends IPropertyProvider> getType() {
        return getClass();
    }

    @Override
    public Array<PropertyWidget> getListOfProperties () {
        Array<PropertyWidget> properties = new Array<>();

        AssetSelectWidget textureWidget = new AssetSelectWidget("Texture", GameAssetType.SPRITE, new Supplier<GameAsset<Texture>>() {
            @Override
            public GameAsset<Texture> get() {
                return gameAsset;
            }
        }, new PropertyWidget.ValueChanged<GameAsset<Texture>>() {
            @Override
            public void report(GameAsset<Texture> value) {
                setGameAsset(value);
            }
        });

        PropertyWidget colorWidget = WidgetFactory.generate(this, "color", "Color");
        PropertyWidget flipXWidget = WidgetFactory.generate(this, "flipX", "Flip X");
        PropertyWidget flipYWidget = WidgetFactory.generate(this, "flipY", "Flip Y");
        PropertyWidget renderModesWidget = WidgetFactory.generate(this, "renderMode", "Render Mode");

        properties.add(textureWidget);
        properties.add(colorWidget);
        properties.add(flipXWidget);
        properties.add(flipYWidget);
        properties.add(renderModesWidget);

        Array<PropertyWidget> superList = super.getListOfProperties();
        properties.addAll(superList);

        return properties;
    }

    @Override
    public String getPropertyBoxTitle () {
        return "Sprite Renderer";
    }

    @Override
    public int getPriority () {
        return 2;
    }

    GameAsset.GameAssetUpdateListener gameAssetUpdateListener = new GameAsset.GameAssetUpdateListener() {
        @Override
        public void onUpdate () {
            if (gameAsset.isBroken()) {
                textureRegion = AssetRepository.getInstance().brokenTextureRegion;
            } else {
                textureRegion = new TextureRegion(gameAsset.getResource());
            }
        }
    };

    private void loadTextureFromIdentifier (String gameResourceIdentifier) {
        GameAsset<Texture> assetForIdentifier = AssetRepository.getInstance().getAssetForIdentifier(gameResourceIdentifier, GameAssetType.SPRITE);
        setGameAsset(assetForIdentifier);
    }

    @Override
    public void write (Json json) {
        GameResourceOwner.writeGameAsset(json, this);

        json.writeValue("color", color);
        json.writeValue("flipX", flipX);
        json.writeValue("flipY", flipY);
        json.writeValue("renderMode", renderMode);

        super.write(json);

    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        String gameResourceIdentifier = jsonData.getString("gameResource", "");//Don't need to use it, we use path

        loadTextureFromIdentifier(gameResourceIdentifier);

        color = json.readValue(Color.class, jsonData.get("color"));
        if(color == null) color = new Color(Color.WHITE);

        flipX = jsonData.getBoolean("flipX", false);
        flipY = jsonData.getBoolean("flipY", false);
        renderMode = json.readValue(RenderMode.class, jsonData.get("renderMode"));
        if(renderMode == null) renderMode = RenderMode.simple;

        super.read(json, jsonData);
    }

    public TextureRegion getTextureRegion () {
        return textureRegion;
    }

}
