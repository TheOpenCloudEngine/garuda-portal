package org.uengine.bss.monetization.entity;

/**
 * Created by hoo.lim on 6/29/2015.
 */
public class Comtable {
    String comcode;
    String comname;
    String description;
    String isdeleted;
    String repmail;
    String repMlHst;
    String repMlPwd;
    String alias;
    String logoPath;

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getRepmail() {
        return repmail;
    }

    public void setRepmail(String repmail) {
        this.repmail = repmail;
    }

    public String getRepMlHst() {
        return repMlHst;
    }

    public void setRepMlHst(String repMlHst) {
        this.repMlHst = repMlHst;
    }

    public String getRepMlPwd() {
        return repMlPwd;
    }

    public void setRepMlPwd(String repMlPwd) {
        this.repMlPwd = repMlPwd;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Override
    public String toString() {
        return "Comtable{" +
                "comcode='" + comcode + '\'' +
                ", comname='" + comname + '\'' +
                ", description='" + description + '\'' +
                ", isdeleted='" + isdeleted + '\'' +
                ", repmail='" + repmail + '\'' +
                ", repMlHst='" + repMlHst + '\'' +
                ", repMlPwd='" + repMlPwd + '\'' +
                ", alias='" + alias + '\'' +
                ", logoPath='" + logoPath + '\'' +
                '}';
    }
}
