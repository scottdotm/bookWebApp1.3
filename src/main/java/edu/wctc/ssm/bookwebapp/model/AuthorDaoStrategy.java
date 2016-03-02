/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Scott
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;

    abstract int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;

    public abstract int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException;

    public abstract int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException;
    
    public abstract Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;

    public DBStrategy getDb();

    public void setDb(DBStrategy db);

    public void initDao(String driver, String url, String user, String pass, String table, String colone, String coltwo, String primarykey);

    public void setDRIVER(String DRIVER);

    public void setURL(String URL);

    public void setUSER(String USER);

    public void setPASSWORD(String PASSWORD);
    
    public void setTable(String table);
    
    public void setColone(String colone);
    
    public void setColtwo(String coltwo);
    
    public void setPrimarykey(String primarykey);
    
    public String getPrimarykey();
    
    public String getColtwo();
    
    public String getColone();
    
    public String getTable();

    public String getPASSWORD();

    public String getUSER();

    public String getURL();

    public String getDRIVER();

}
