/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * 
 * @author Scott
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy,Serializable {

    @Inject
    private DBStrategy db;
    private String driver;
    private String url;
    private String user;
    private String pass;
    private String table;
    private String colone;
    private String coltwo;
    private String primarykey;
    private final List COLNAMES = new ArrayList();
    private final List VALUES = new ArrayList();
    
    
    /**
     * Blank Constructor for injectable objects
     */
    public AuthorDao() {
    }
    
    /**
     *
     * @param driver
     * @param url
     * @param user
     * @param pass
     * @param table
     * @param colone
     * @param primarykey
     * @param coltwo
     */
    @Override
    public void initDao(String driver, String url, String user, String pass, String table, String colone, String coltwo, String primarykey){
        setDRIVER(driver);
        setURL(url);
        setUSER(user);
        setPASSWORD(pass);
        setTable(table);
        setColone(colone);
        setColtwo(coltwo);
        setPrimarykey(primarykey);
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pass);

        List<Map<String, Object>> rawData
                = db.findAllRecords(table, 0);
        List<Author> authors = new ArrayList<>();

        for (Map rec : rawData) {
            Author author = new Author();
            Integer id = new Integer(rec.get(primarykey).toString());
            author.setAuthorId(id);
            String name = rec.get(colone) == null ? "" : rec.get(colone).toString();
            author.setAuthorName(name);
            Date date = rec.get(coltwo) == null ? null : (Date) rec.get(coltwo);
            author.setDateAdded(date);
            authors.add(author);
        }

        db.closeConnection();

        return authors;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pass);
        int result = db.deleteById(table, primarykey, id);
        db.closeConnection();
        return result;
    }
    
    /**
     *
     * @param name
     * @param date
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pass);
        COLNAMES.add(colone);
        COLNAMES.add(coltwo);
        VALUES.add(name);
        VALUES.add(date);
        int result = db.insertOneRecord(table, COLNAMES, VALUES);
        COLNAMES.clear();
        VALUES.clear();
        db.closeConnection();
        return result;
    }
    
    /**
     *
     * @param id
     * @param name
     * @param date
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException{
        COLNAMES.add(colone);
        COLNAMES.add(coltwo);
        VALUES.add(name);
        VALUES.add(date);
        db.openConnection(driver, url, user, pass);
        int result = db.updateRecordById(table, COLNAMES, VALUES, primarykey, id);
        COLNAMES.clear();
        VALUES.clear();
        db.closeConnection();
        return result;
    }

    /**
     *
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pass);
        
        Map<String,Object> rawRec = db.findById(table, primarykey, authorId);
        Author author = new Author();
        author.setAuthorId((Integer)rawRec.get(primarykey));
        author.setAuthorName(rawRec.get(colone).toString());
        author.setDateAdded((Date)rawRec.get(coltwo));
        
        return author;
    }
    
    //Getters and Setters

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getColone() {
        return colone;
    }

    @Override
    public String getColtwo() {
        return coltwo;
    }

    @Override
    public String getPrimarykey() {
        return primarykey;
    }
    
    

    @Override
    public String getDRIVER() {
        return driver;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getUSER() {
        return user;
    }

    @Override
    public String getPASSWORD() {
        return pass;
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public void setColone(String colone) {
        this.colone = colone;
    }

    @Override
    public void setColtwo(String coltwo) {
        this.coltwo = coltwo;
    }

    @Override
    public void setPrimarykey(String primarykey) {
        this.primarykey = primarykey;
    }
    
    

    @Override
    public void setDRIVER(String DRIVER) {
        this.driver = DRIVER;
    }

    @Override
    public void setURL(String URL) {
        this.url = URL;
    }

    @Override
    public void setUSER(String USER) {
        this.user = USER;
    }

    @Override
    public void setPASSWORD(String PASSWORD) {
        this.pass = PASSWORD;
    }

    @Override
    public DBStrategy getDb() {
        return db;
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorDaoStrategy dao = new AuthorDao();
//        Date date = new Date();
//        String name = "John Doe";
//        dao.createOneAuthor(name, date);
//        dao.updateAuthor(28, "Jim L.", 0);
//        List <Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//    }
}
