package nl.jortenmilo.plugin;

import java.net.URLClassLoader;
import java.util.List;

public class LoadedPlugin {
	
	private Plugin plugin;
	private String name;
	private String path;
	private URLClassLoader loader;
	private List<LoadedPlugin> dependencies;
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	protected void setPath(String path) {
		this.path = path;
	}

	public URLClassLoader getClassLoader() {
		return loader;
	}

	protected void setClassLoader(URLClassLoader loader) {
		this.loader = loader;
	}
	
	protected void addDependency(LoadedPlugin plugin) {
		dependencies.add(plugin);
	}
	
	public List<LoadedPlugin> getDependencies() {
		return dependencies;
	}
}