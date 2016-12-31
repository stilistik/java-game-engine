package component;

import java.util.HashMap;
import java.util.Map;

public class ComponentContainer {
	  private Map<Class<?>, Component> components =
	    new HashMap<Class<?>, Component>();

	  public <T> void put(Class<T> c, Component component) {
	    components.put(c, component);
	  }

	  public <T> T get(Class<T> c) {
	    return c.cast(components.get(c));
	  }
	  
	  public void update(){
			for (Class<?> type : components.keySet()){
				((Component) components.get(type)).update();
			}
	  }
}
