/*******************************************************************************
 * Copyright 2019 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.talosvfx.talos.editor.wrappers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.talosvfx.talos.TalosMain;
import com.talosvfx.talos.editor.widgets.ui.DragPoint;
import com.talosvfx.talos.editor.widgets.ui.PreviewWidget;
import com.talosvfx.talos.runtime.modules.Vector2Module;
import com.talosvfx.talos.runtime.modules.Vector3Module;

public class Vector3ModuleWrapper extends ModuleWrapper<Vector3Module> implements IDragPointProvider {

	private VisTextField xField;
	private VisTextField yField;
	private VisTextField zField;

	private DragPoint dragPoint;

	@Override
	public void setModule(Vector3Module module) {
		super.setModule(module);
		xField.setText(module.getDefaultX() + "");
		yField.setText(module.getDefaultY() + "");
		zField.setText(module.getDefaultZ() + "");
	}

	@Override
	protected void configureSlots () {

		xField = addInputSlotWithTextField("X: ", 0);
		yField = addInputSlotWithTextField("Y: ", 1);
		zField = addInputSlotWithTextField("z: ", 2);

		dragPoint = new DragPoint(0, 0);

		xField.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float x = floatFromText(xField);
				module.setX(x);

				//todo 3d drag
//				dragPoint.set(x, dragPoint.position.y);
			}
		});

		yField.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float y = floatFromText(yField);
				module.setY(y);

				//todo 3d drag
//				dragPoint.set(dragPoint.position.x, y);
			}
		});

		zField.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float z = floatFromText(zField);
				module.setZ(z);

				//todo 3d drag
//				dragPoint.set(dragPoint.position.x, y);
			}
		});

		addOutputSlot("position", 0);
	}

	@Override
	protected void wrapperSelected() {
		PreviewWidget previewWidget = TalosMain.Instance().UIStage().PreviewWidget();
		previewWidget.registerForDragPoints(this);
	}

	@Override
	protected void wrapperDeselected() {
		PreviewWidget previewWidget = TalosMain.Instance().UIStage().PreviewWidget();
		previewWidget.unregisterDragPoints(this);
	}

	@Override
	protected float reportPrefWidth () {
		return 210;
	}

	@Override
	public void read (Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		xField.setText(module.getDefaultX() + "");
		yField.setText(module.getDefaultY() + "");
		zField.setText(module.getDefaultZ() + "");

		//todo 3d drag
//		dragPoint.set(module.getDefaultX(), module.getDefaultY());
	}

	@Override
	public DragPoint[] fetchDragPoints() {
		return new DragPoint[]{dragPoint};
	}

	@Override
	public void dragPointChanged(DragPoint point) {
		//todo 3d drag

//		module.setX(point.position.x);
//		module.setY(point.position.y);
//
//		xField.setText(module.getDefaultX() + "");
//		yField.setText(module.getDefaultY() + "");
	}
}
