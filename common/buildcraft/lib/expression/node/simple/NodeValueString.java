package buildcraft.lib.expression.node.simple;

import buildcraft.lib.expression.api.Arguments;
import buildcraft.lib.expression.api.IExpressionNode.INodeString;

public class NodeValueString implements INodeString {
    public final String value;

    public NodeValueString(String value) {
        this.value = value;
    }

    @Override
    public String evaluate() {
        return value;
    }

    @Override
    public INodeString inline(Arguments args) {
        return this;
    }
}
