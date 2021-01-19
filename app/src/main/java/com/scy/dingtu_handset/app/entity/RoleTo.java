package com.scy.dingtu_handset.app.entity;

import java.util.List;

public class RoleTo {

    /**
     * Id : string
     * Name : string
     * Modules : ["string"]
     * ModulesAsString : string
     * State : 0
     * CompanyCode : 0
     */

    private String Id;
    private String Name;
    private String ModulesAsString;
    private int State;
    private int CompanyCode;
    private List<String> Modules;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getModulesAsString() {
        return ModulesAsString;
    }

    public void setModulesAsString(String ModulesAsString) {
        this.ModulesAsString = ModulesAsString;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(int CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public List<String> getModules() {
        return Modules;
    }

    public void setModules(List<String> Modules) {
        this.Modules = Modules;
    }
}
