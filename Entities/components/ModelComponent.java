package components;

import architecture.Component;
import architecture.ComponentType;
import entity.Entity;
import model.Model;

public class ModelComponent implements Component {

	private Entity entity;
	private Model model;

	public ModelComponent(Entity entity, Model model){
		this.entity = entity;
		this.model = model;
	}
	
	@Override
	public void update() {}
	
	public Model getModel(){
		return model;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.MODEL;
	}
	
}
