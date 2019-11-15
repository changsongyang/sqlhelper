package com.jn.sqlhelper.common.connection;

import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.configuration.Configuration;
import com.jn.langx.util.Objects;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.diff.MapDiffResult;
import com.jn.langx.util.function.Consumer2;

import java.util.Map;
import java.util.Properties;

public class NamedConnectionConfiguration extends ConnectionConfiguration implements Configuration, Cloneable {
    private String name;
    private String catalog;
    private String schema;

    public NamedConnectionConfiguration() {

    }

    public NamedConnectionConfiguration(ConnectionConfiguration configuration) {
        setDriver(configuration.getDriver());
        setUser(configuration.getUser());
        setPassword(configuration.getPassword());
        setUrl(configuration.getUrl());
        final Properties props = new Properties();
        Collects.forEach(configuration.getDriverProps(), new Consumer2<Object, Object>() {
            @Override
            public void accept(Object key, Object value) {
                props.setProperty(key.toString(), value.toString());
            }
        });

        setDriverProps(props);
    }

    @Override
    public void setId(String id) {
        name = id;
    }

    @Override
    public String getId() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return JSONBuilderProvider.simplest().toJson(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NamedConnectionConfiguration)) {
            return false;
        }
        NamedConnectionConfiguration o2 = (NamedConnectionConfiguration) obj;
        if (!name.equals(o2.getName()) || !getDriver().equals(o2.getDriver()) || !getId().equals(o2.getId()) || !getUrl().equals(o2.getUrl()) || !getUser().equals(o2.getUser())) {
            return false;
        }

        if (getPassword() != null) {
            if (!getPassword().equals(o2.getPassword())) {
                return false;
            }
        } else {
            if (o2.getPassword() != null) {
                return false;
            }
        }

        String schema1 = Strings.getNullIfBlank(schema);
        String schema2 = Strings.getNullIfBlank(o2.getSchema());
        if (!Objects.equals(schema1, schema2)) {
            return false;
        }

        String catalog1 = Strings.getNullIfBlank(catalog);
        String catalog2 = Strings.getNullIfBlank(o2.getCatalog());
        if (!Objects.equals(catalog1, catalog2)) {
            return false;
        }

        Map<String, String> map1 = Collects.propertiesToStringMap(getDriverProps(), true);
        Map<String, String> map2 = Collects.propertiesToStringMap(o2.getDriverProps(), true);
        MapDiffResult<String, String> diffResult = Collects.diff(map1, map2);
        return diffResult.getAdds().isEmpty() && diffResult.getRemoves().isEmpty() && diffResult.getUpdates().isEmpty();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        NamedConnectionConfiguration conn = new NamedConnectionConfiguration(this);
        conn.setName(name);
        conn.setId(name);
        conn.setCatalog(catalog);
        conn.setSchema(schema);
        return conn;
    }
}
