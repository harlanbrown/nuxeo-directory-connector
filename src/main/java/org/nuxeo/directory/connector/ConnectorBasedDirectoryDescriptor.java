package org.nuxeo.directory.connector;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XNodeList;
import org.nuxeo.common.xmap.annotation.XNodeMap;
import org.nuxeo.common.xmap.annotation.XObject;
import org.nuxeo.ecm.directory.InverseReference;
import org.nuxeo.ecm.directory.Reference;

@XObject("directory")
public class ConnectorBasedDirectoryDescriptor implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @XNode("@name")
    protected String name;

    @XNode("schema")
    protected String schema;

    @XNode("@class")
    protected Class<? extends EntryConnector> connectorClass;

    @XNode("idField")
    public String idField;

    @XNode("passwordField")
    private String passwordField;

    @XNodeMap(value = "parameters/parameter", key = "@name", type = HashMap.class, componentType = String.class)
    protected Map<String, String> parameters = new HashMap<String, String>();

    @XNodeList(value = "references/inverseReference", type = InverseReference[].class, componentType = InverseReference.class)
    private InverseReference[] inverseReferences;

    public Reference[] getInverseReferences() {
        return inverseReferences;
    }

    public Reference[] getTableReferences() {
        return null;
    }

    protected EntryConnector connector = null;

    public EntryConnector getConnector() {
        if (connector == null) {
            try {
                connector = (EntryConnector) connectorClass.newInstance();
                connector.init(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connector;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    public String getSchemaName() {
        return schema;
    }

    public String getIdField() {
        return idField;
    }

    public String getPasswordField() {
        return passwordField;
    }
}
