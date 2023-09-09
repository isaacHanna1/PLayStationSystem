/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Seha
 */
public class repository {
    
    int repositoryID ;
    String repositoryName;

    public repository(int repositoryID, String repositoryName) {
        this.repositoryID = repositoryID;
        this.repositoryName = repositoryName;
    }

    public int getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(int repositoryID) {
        this.repositoryID = repositoryID;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    
    
}
