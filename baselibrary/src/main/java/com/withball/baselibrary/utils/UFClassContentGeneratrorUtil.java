package com.withball.baselibrary.utils;

import com.withball.baselibrary.beans.UFClass;
import com.withball.baselibrary.beans.UFColumnItem;
import com.withball.baselibrary.enums.UFColumnType;
import com.withball.baselibrary.enums.UFPrimaryKeyType;

import java.util.List;

/**
 * 类名：UFClassContentGeneratrorUtil
 * 描述：生成类中的内容
 * 包名： com.withball.jfmlibrary.utils
 * 项目名：Jfm_android
 * Created by qinzongke on 8/30/16.
 */
public class UFClassContentGeneratrorUtil {

    private static final String PACKAGE = "package com.withball.jfm_android.model;\n";

    private static final String PUBLICCLASS = "public class ";

    private static final String PUBLIC = "public ";

    private static final String STATIC = "static ";

    private static final String FINAL = " final ";

    private static final String STRING = " String ";

    private static final String PRIVATE = "private ";

    private static final String GET = "get";

    private static final String SET = "set";

    private static final String VOID = "void ";


    private static final String END = ";";

    private static final String LEFT = "{\n";

    private static final String EQUAL = " = ";

    private static final String LE = "(";

    private static final String RE = ")";

    private static final String RIGHT = "}";

    private static final String RETURN = "return ";

    private static final String CREATETABLE = "CREATE TABLE ";




    public static String generatorContent(UFClass classes) {
        StringBuffer content = new StringBuffer();
        content.append(PACKAGE).append("\n");
        //import
        content.append("import com.withball.jfmlibrary.UFModel;\n");
        content.append("import com.withball.jfmlibrary.constants.UFDBConstants;\n");
        content.append("import com.withball.baselibrary.annotations.UFTableSql;\n");
        String sql = createTableString(classes);
        content.append("\n@UFTableSql(value = \"").append(sql).append("\")\n");
        //public class ***{
        content.append(PUBLICCLASS).append(UFStringUtil.getModelName(classes)).append("  <M extends ").append(UFStringUtil.getModelName(classes)).append("<M>>").append(" extends UFModel<M>").append(LEFT);
        List<UFColumnItem> columnItems = classes.getColumns();
        if (classes.isHasPrimaryKey()) {
            UFColumnItem primaryKey = classes.getPrimaryKey();
            columnItems.add(0,primaryKey);
        }
        content.append("\n\n\t").append(PUBLIC).append(STATIC).append(FINAL).append(STRING).append("TABLENAME ").append(" = \"").append(UFStringUtil.getTableName(classes)).append("\"").append(END);
        int len = columnItems.size();
        UFColumnItem item = null;
        for(int i = 0;i<len;i++){
            item = columnItems.get(i);
            content.append("\n\n\t").append(PUBLIC).append(STATIC).append(FINAL).append(STRING).append(item.getColumnName().toUpperCase()).append(" = \"").append(item.getColumnName()).append("\"").append(END);
        }
        for(int i = 0;i<len;i++){
            item = columnItems.get(i);
            content.append("\n\n\t").append(PRIVATE).append(UFColumnType.getColumnType(item.getColumnType())).append(item.getColumnName()).append(END);
        }
        //contruct
        content.append("\n\n\t").append(PUBLIC).append(UFStringUtil.getModelName(classes)).append(LE).append(RE).append(LEFT);
        content.append("\t\t").append("set(UFDBConstants.TABLENAME,").append("\"").append(UFStringUtil.getTableName(classes)).append("\"").append(");\n");
        content.append("\t").append(RIGHT).append("\n");
        //生成crateTableSql
        for (int i = 0; i < len; i++) {
            item = columnItems.get(i);
            content.append("\n\n\t").append(PUBLIC).append(UFColumnType.getColumnType(item.getColumnType())).append(GET).append(UFStringUtil.firstLetterToUp(item.getColumnName())).append(LE).append(RE).append(LEFT);
            content.append("\t\t").append(RETURN).append("get(\"").append(item.getColumnName()).append("\")").append(END);
            content.append("\n\t").append(RIGHT);
            content.append("\n\n\t").append(PUBLIC).append(VOID).append(SET).append(UFStringUtil.firstLetterToUp(item.getColumnName())).append(LE).append(UFColumnType.getColumnType(item.getColumnType())).append(" ").append(item.getColumnName()).append(RE).append(LEFT);
            content.append("\t\t").append("set(\"").append(item.getColumnName()).append("\",").append(item.getColumnName()).append(")").append(END);
            content.append("\n\t").append(RIGHT);
        }
        content.append("\n").append(RIGHT);
        return content.toString();
    }



    /**
     * create table
     *
     * @param clazz
     * @return
     */
    private static String createTableString(UFClass clazz) {
        StringBuffer sql = new StringBuffer();
        String tableName = UFStringUtil.getTableName(clazz);
        sql.append(CREATETABLE).append(tableName).append(LE);
        UFColumnItem item = null;
        List<UFColumnItem> columns = clazz.getColumns();
        int len = columns.size();
        for (int i = 0; i < len; i++) {
            item = columns.get(i);
            sql.append(item.getColumnName()).append(" ").append(UFColumnType.getColumnDBType(item.getColumnType())).append(",");
        }
        if(clazz.isHasPrimaryKey()){
            sql.append(clazz.getPrimaryKey().getColumnName());
            if (UFPrimaryKeyType.AUTOINCREMENT == clazz.getPrimaryKeyType()) {
                sql.append(" Integer PRIMARY KEY").append(" ").append(UFPrimaryKeyType.AUTOINCREMENT);
            }else{
                sql.append(" ").append(UFColumnType.getColumnDBType(clazz.getPrimaryKey().getColumnType())).append(" PRIMARY KEY");
            }
        }
        if (sql.toString().endsWith(",")) {
            sql = new StringBuffer(sql.subSequence(0, sql.length() - 1));
        }
        sql.append(RE);
        return sql.toString();
    }




}
