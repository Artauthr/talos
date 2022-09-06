package com.talosvfx.talos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.talosvfx.talos.editor.layouts.LayoutColumn;
import com.talosvfx.talos.editor.layouts.LayoutContent;
import com.talosvfx.talos.editor.layouts.LayoutGrid;

public class LayoutTests extends ApplicationAdapter {

	Skin skin;
	private Stage stage;
	private LayoutGrid layoutGrid;

	@Override
	public void create () {
		super.create();

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		skin.addRegions(atlas);

		VisUI.load(skin);

		stage = new Stage();

		InputAdapter debugProcessor = new InputAdapter() {
			@Override
			public boolean keyDown (int keycode) {
				if (Input.Keys.SPACE == keycode) {
					refresh();
				}
				if (Input.Keys.N == keycode) {
					newItem();
				}
				return super.keyDown(keycode);
			}
		};
		Gdx.input.setInputProcessor(new InputMultiplexer(debugProcessor, stage));

		refresh();
	}

	private void newItem () {
		LayoutContent content = new LayoutContent(skin, layoutGrid);

		int random = MathUtils.random(1,3);
		for (int i = 0; i < random; i++) {
			content.addContent("NewApplication: " + MathUtils.random(100));
		}

		layoutGrid.addContent(content);
	}
	private void refresh () {
		stage.clear();


		layoutGrid = new LayoutGrid(skin);
		LayoutContent content = new LayoutContent(skin, layoutGrid);
		content.addContent("Random");
		layoutGrid.addContent(content);

		layoutGrid.setFillParent(true);

		stage.addActor(layoutGrid);
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setMaximized(true);
		config.setTitle("LayoutTest");
		config.useVsync(true);
		config.setBackBufferConfig(1,1,1,1,8,8, 16);
		config.setWindowIcon("icon/talos-64x64.png");

		new Lwjgl3Application(new LayoutTests(), config);

	}
}
