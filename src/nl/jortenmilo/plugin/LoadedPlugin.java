package nl.jortenmilo.plugin;

import java.net.URLClassLoader;
import java.util.List;

/**
 */
public class LoadedPlugin {
	
	private Plugin plugin;
	private String name;
	private String path;
	private String author;
	private String website;
	private String version;
	private URLClassLoader loader;
	private List<String> dependencies;
	
	/**
	 * Returns the Plugin this class contains information about.
	 * @return The Plugin
	 * @see Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}
	
	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns the name of the Plugin.
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the path of the main class of this plugin. The main class will be loaded first and instantiated.
	 * @return The path
	 */
	public String getPath() {
		return path;
	}
	
	protected void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Returns the URLClassLoader of this plugin. The URLClassLoader is used to load the main class, the rest follows automatically.
	 * @return The URLClassLoader
	 */
	public URLClassLoader getClassLoader() {
		return loader;
	}

	protected void setClassLoader(URLClassLoader loader) {
		this.loader = loader;
	}
	
	protected void addDependency(String plugin) {
		dependencies.add(plugin);
	}
	
	/**
	 * Returns a list of dependencies that this plugin uses. 
	 * A dependency means that this plugin will load after the plugin it depends on.
	 * @return A list of dependencies
	 */
	public List<String> getDependencies() {
		return dependencies;
	}

	/**
	 * Returns the name of the author of this plugin. May be null of it wasn't specified in the plugin.jcio file.
	 * @return The name of the author
	 */
	public String getAuthor() {
		return author;
	}

	protected void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Returns the website of the author of this plugin. May be null of it wasn't specified in the plugin.jcio file.
	 * @return The website of the author
	 */
	public String getWebsite() {
		return website;
	}

	protected void setWebsite(String website) {
		this.website = website;
	}
	
	/**
	 * Returns the version of this plugin. May be null of it wasn't specified in the plugin.jcio file.
	 * @return The version
	 */
	public String getVersion() {
		return version;
	}

	protected void setVersion(String version) {
		this.version = version;
	}
}