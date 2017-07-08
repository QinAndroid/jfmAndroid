package com.withball.jfmlibrary;


import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.jfmlibrary.idc.IDC;
import com.withball.jfmlibrary.kits.UFDefaultValueKits;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类名：UFModel
 * 描述：
 * 包名： com.withball.jfmlibrary
 * 项目名：Jfm_android
 * Created by qinzongke on 8/31/16.
 */
public abstract class UFModel<M extends UFModel> {


    /**
     * Attributes of this model
     */
    private Map<String, Object> attrs = new HashMap<String, Object>();


    protected UFModel() {
    }

    /**
     * Set attribute to model.
     *
     * @param attr  the attribute name of the model
     * @param value the value of the attribute
     * @return this model
     */
    protected M set(String attr, Object value) {
        return this.setAttr(attr, value);
    }


    /**
     * Put key value pair to the model without check attribute name.
     */
    public M put(String key, Object value) {
        return this.set(key, value);
    }

    /**
     * Put map to the model without check attribute name.
     */
    public M put(Map<String, Object> map) {
        attrs.putAll(map);
        return (M) this;
    }

    /**
     * Put other model to the model without check attribute name.
     */
    public M put(UFModel model) {
        attrs.putAll(model.getAttrs());
        return (M) this;
    }

    /**
     * Get attribute of any mysql type
     */
    public <T> T get(String attr) {
        if(!attrs.containsKey(attr)){
            return null;
        }
        return this.get(attr, UFDefaultValueKits.getDefaultValue(attrs.get(attr)));
    }



    /**
     * Get attribute of any mysql type. Returns defaultValue if null.
     */
    public <T> T get(String attr, Object defaultValue) {
        Object result = attrs.get(attr);
        return (T) (result != null ? result : defaultValue);
    }

    /**
     * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
     * mediumtext, longtext
     */
    public String getStr(String attr) {
        return this.get(attr, "");//(String) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint,
     * mediumint
     */
    public Integer getInt(String attr) {
        return this.get(attr, 0);//(Integer) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: bigint, unsign int
     */
    public Long getLong(String attr) {
        return this.get(attr, 0L);//(Long) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: unsigned bigint
     */
    public java.math.BigInteger getBigInteger(String attr) {
        return this.get(attr, new java.math.BigInteger("0"));//(java.math.BigInteger) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: date, year
     */
    public java.util.Date getDate(String attr) {
        return this.get(attr, new java.util.Date());//(java.util.Date) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: time
     */
    public java.sql.Time getTime(String attr) {
        return this.get(attr, new java.util.Date());//(java.sql.Time) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: timestamp, datetime
     */
    public java.sql.Timestamp getTimestamp(String attr) {
        return this.get(attr, new java.util.Date());//(java.sql.Timestamp) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: real, double
     */
    public Double getDouble(String attr) {
        return this.get(attr, 0.0);//(Double) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: float
     */
    public Float getFloat(String attr) {
        return this.get(attr, 0.0);//(Float) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: bit, tinyint(1)
     */
    public Boolean getBoolean(String attr) {
        return this.get(attr, Boolean.FALSE);// (Boolean) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: decimal, numeric
     */
    public java.math.BigDecimal getBigDecimal(String attr) {
        return this.get(attr, new java.math.BigDecimal(0));//(java.math.BigDecimal) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
     * mediumblob, longblob
     */
    public byte[] getBytes(String attr) {
        return this.get(attr, new byte[0]);//(byte[]) attrs.get(attr);
    }

    /**
     * Get attribute of any type that extends from Number
     */
    public Number getNumber(String attr) {
        return this.get(attr, 0); //(Number) attrs.get(attr);
    }

    /**
     * Return attribute Map.
     * <p>
     * Danger! The update method will ignore the attribute if you change it
     * directly. You must use set method to change attribute that update method
     * can handle it.
     */
    protected Map<String, Object> getAttrs() {
        return attrs;
    }


    private Map<String, String> attrContect = new HashMap<>();

    private Map<String, String> attrOpts = new HashMap<>();


    /**
     * Set attribute to model.
     *
     * @param attr  the attribute name of the model
     * @param value the value of the attribute
     * @return this model
     */
    private M setAttr(String attr, Object value) {

        //表名
        if (attr.equals(UFDBConstants.TABLENAME)) {
            this.attrs.put(attr, value);
            return (M) this;
        }

        Object attrVal = value;
        if (Match.matches.containsKey(value)) {
            Match match = Match.matches.get(value);
            attrVal = match.vals;
            if (match.isAndOr()) {
                this.attrContect.put(attr, match.ops);
                this.attrOpts.put(attr, UFModel.EQ);
            } else {
                this.attrContect.put(attr, UFModel.AND);
                this.attrOpts.put(attr, match.ops);
            }
        } else {
            this.attrContect.put(attr, UFModel.AND);
            this.attrOpts.put(attr, UFModel.EQ);
        }
        this.attrs.put(attr, attrVal);
        return (M) this;
    }


    /**
     * Remove attributes if it is null.
     *
     * @return this model
     */
    public M removeNullValueAttrs() {
        for (Iterator<Map.Entry<String, Object>> it = attrs.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> e = it.next();
            if (e.getValue() == null) {
                it.remove();
            }
        }
        return (M) this;
    }

    /**
     * DC Data prepare
     */
    public Map<String, Object> dcExport() {
        this.attrs.put(UFDBConstants.CONNECT, this.attrContect);
        this.attrs.put(UFDBConstants.CONDITION, this.attrOpts);
        return this.removeNullValueAttrs().getAttrs();
    }


    private static final String LIKE = " LIKE ";
    private static final String EQ = " = ";
    private static final String NEQ = " != ";
    private static final String BETWEEN = " BETWEEN ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String GE = " >= ";
    private static final String GT = " > ";
    private static final String LE = " <= ";
    private static final String LT = " < ";

    public static class Match {

        private Object mq = null;
        private String ops = "";
        private Object[] vals = null;

        private static Map<Object, Match> matches = new HashMap<Object, Match>();

        private Match(Object mq, String ops, Object... vals) {
            this.mq = mq;
            this.ops = ops;
            this.vals = vals;
        }


        private static <T> T getMq(T t) {
            return UFDefaultValueKits.getDefaultValue(t);
        }

        private boolean isAndOr() {
            return UFModel.AND.equals(this.ops) || UFModel.OR.equals(this.ops);
        }

        /**
         * column = 'value'
         */
        public static <T> T EQ(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.EQ, value));
            return mq;
        }

        /**
         * column != 'value'
         */
        public static <T> T NEQ(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.NEQ, value));
            return mq;
        }

        /**
         * column LIKE 'value'
         */
        public static <T> T LIKE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.LIKE, value));
            return mq;
        }

        /**
         * column >= 'value'
         */
        public static <T> T GE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.GE, value));
            return mq;
        }

        /**
         * column > 'value'
         */
        public static <T> T GT(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.GT, value));
            return mq;
        }

        /**
         * column <= 'value'
         */
        public static <T> T LE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.LE, value));
            return mq;
        }

        /**
         * column < 'value'
         */
        public static <T> T LT(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.LT, value));
            return mq;
        }

        /**
         * column1 AND column2
         */
        public static <T> T AND(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.AND, value));
            return mq;
        }

        /**
         * column1 OR column2
         */
        public static <T> T OR(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, UFModel.OR, value));
            return mq;
        }

        /**
         * BETWEEN 'start' AND 'end'
         */
        public static <T> T BW(T start, T end) {
            T mq = Match.getMq(start);
            Match.matches.put(mq, new Match(mq, UFModel.BETWEEN, start, end));
            return mq;
        }
    }

    public static class QueryOut<M> {

        private Map<String, Object> attrs = new HashMap<String, Object>();

        public QueryOut(Map<String, Object> map) {
            this.attrs.putAll(map);
        }


        public List<M> getAll() {
            return (List<M>) this.attrs.get(UFDBConstants.QUERYDATA);
        }

        public M getFirst() {
           return (M)this.attrs.get(UFDBConstants.QUERYDATA);
        }

        public int getQueryCount() {
            return Integer.parseInt(this.attrs.get(UFDBConstants.QUERYDATACOUNT).toString());
        }


    }


    private IDC mIDC = null;

    public void setIdc(IDC idc) {
        this.mIDC = idc;
    }
    private void checkIDC() {
        if (null == mIDC) {
            throw new IllegalArgumentException("idc is null");
        }
    }


    /**
     * Set Orders
     * @param orders
     */
    public M setOrders(String... orders) {
        this.attrs.put(UFDBConstants.ORDERKEY, orders);
        return (M) this;
    }

    public void setOutField(String...fields){
        attrs.put(UFDBConstants.OUTPUTFIELD,fields);
    }

    public QueryOut queryAll() {
        checkIDC();
        return mIDC.queryAll(this);
    }

    public QueryOut querFirst() {
        checkIDC();
        return mIDC.queryFirst(this);
    }

    public void save() {
        checkIDC();
        mIDC.save(this);
    }

    public void update() {
        checkIDC();
        mIDC.update(this);
    }

    public void delete() {
        checkIDC();
        mIDC.delete(this);
    }


}
