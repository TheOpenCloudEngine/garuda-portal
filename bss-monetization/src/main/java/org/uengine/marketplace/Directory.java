package org.uengine.marketplace;

import org.metaworks.annotation.Children;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;
import org.uengine.application.App;

import java.util.ArrayList;
import java.util.List;

@Face(ejsPath="dwr/metaworks/genericfaces/TreeNodeFace.ejs")
public class Directory {

    String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    String name;
    @Name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    List<Directory> childDirectory = new ArrayList<Directory>();
    @Children
        public List<Directory> getChildDirectory() {
            return childDirectory;
        }

        public void setChildDirectory(List<Directory> childDirectory) {
            this.childDirectory = childDirectory;
        }

    List<App> childApps = new ArrayList<App>();
    @Children
        public List<App> getChildApps() {
            return childApps;
        }

        public void setChildApps(List<App> childApps) {
            this.childApps = childApps;
        }

    public void Directory(){

        setName("root");

        {
            Directory child = new Directory();
            child.setName("child1");
            getChildDirectory().add(child);
        }

        {
            Directory child = new Directory();
            child.setName("child2");
            getChildDirectory().add(child);
        }

        {
            Directory child = new Directory();
            child.setName("child3");
            getChildDirectory().add(child);
        }




    }

}
