Creating a plugin is very easy! Well, you have to know some Java syntax though. As I've got a lot of inspiration from Bukkit, people who know how to use Bukkit may find this a lot easier. The tutorial is devided into multiple steps with every step explaining what to do and what it does. 
<br />
<br />
**WARNING:** For all the tutorials I use Eclipse, link: https://eclipse.org/downloads/. It is easy to use but has a LOT of features. It is also neatly designed, and is fully customizable.
You'll also need to have some knowledge of the Java syntax.
<br />
<br />
<br />
**Step 1:**
Right click the "Package Explorer" > Hover over: "New" > Click "Java Project". Now, a window will pop up. Write your plugins name in "Project Name" and than click "Finish". It now created a new project, with two files: "src" and "JRE System Library".
<br />
<br />
**Step 2:**
Right click the project folder > Hover over: "Build Path" > Click: "Add External Archives..." > Now search for the JCIO.jar file and select it. Than click on: "Open". It will now start importing the Archive and a new folder will pop up under the project folder, within the JCIO.jar file.
<br />
<br />
**Step 3:**
Than, Right click the "src" folder > Hover over: "New" > Click: "Class". A window wil popup, and you only have to write the name of your plugin or anything you want in "Name" and than click "Finish". It will create a package with a file.
<br />
<br />
**Step 4:**
Now, your class has to extend "Plugin":
<br />
```java
public class NAME extends Plugin {}
```
<br />
Than import "Plugin" from "nl.jortenmilo.plugin":
<br />
```java
import nl.jortenmilo.plugin.Plugin;
```
<br />
You now have to add some unimplemented methods. This are `enable()` and `disable()`. `enable()` is called when the plugin is enabled and `disable()` is called when the plugin is disabled. Write whatever you like in this methods.
<br />
<br />
**Step 5:**
Now, create a new file in your project. Right click the project folder > Hover over: "New" > Click: "File". A window will pop up. You have to select your project folder and type `plugin.jcio` in "File name". Than click "Finish". In the file you have to write the following things:
<br />
`name: <THIS IS THE NAME OF YOUR PLUGIN>
path: <THIS IS THE PATH OF YOUR MAIN PLUGIN FOLDER>`
<br />
For example:
<br />
`name: Test
path: nl.jortenmilo.test.Test`
<br />
<br />
**Step 6:**
Now you have to export the project. Right click your project > Click: "Export". A window will pop up asking you to choose an export destination. Now select "Java" > Click: "Jar File" > Click "Next" > Than select the destination, probably the plugins folder of your JCIO.jar, and click "Finish". And that's it!
