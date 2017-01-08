package org.apache.velocity.runtime.visitor;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import org.apache.velocity.runtime.parser.Token;
import org.apache.velocity.runtime.parser.node.*;

/**
 * This class is simply a visitor implementation
 * that traverses the AST, produced by the Velocity
 * parsing process, and creates a visual structure
 * of the AST. This is primarily used for
 * debugging, but it useful for documentation
 * as well.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @version $Id: NodeViewMode.java 747106 2009-02-23 19:25:14Z nbubna $
 */
public class NodeViewMode extends BaseVisitor {
    private int indent = 0;
    private boolean showTokens = true;

    /**
     * Indent child nodes to help visually identify
     * the structure of the AST.
     */
    private String indentString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            sb.append("  ");
        }
        return sb.toString();
    }

    /**
     * Display the type of nodes and optionally the
     * first token.
     */
    private Object showNode(Node node, Object data) {
        String tokens = "";
        String special = "";
        Token t;

        if (showTokens) {
            t = node.getFirstToken();

            if (t.specialToken != null && !t.specialToken.image.startsWith("##"))
                special = t.specialToken.image;

            tokens = " -> " + special + t.image;
        }

        System.out.println(indentString() + node + tokens);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;
    }

    /**
     * @see BaseVisitor#visit(SimpleNode, Object)
     */
    public Object visit(SimpleNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTprocess, Object)
     */
    public Object visit(ASTprocess node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTExpression, Object)
     */
    public Object visit(ASTExpression node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTAssignment, Object)
     */
    public Object visit(ASTAssignment node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTOrNode, Object)
     */
    public Object visit(ASTOrNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTAndNode, Object)
     */
    public Object visit(ASTAndNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTEQNode, Object)
     */
    public Object visit(ASTEQNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTNENode, Object)
     */
    public Object visit(ASTNENode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTLTNode, Object)
     */
    public Object visit(ASTLTNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTGTNode, Object)
     */
    public Object visit(ASTGTNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTLENode, Object)
     */
    public Object visit(ASTLENode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTGENode, Object)
     */
    public Object visit(ASTGENode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTAddNode, Object)
     */
    public Object visit(ASTAddNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTSubtractNode, Object)
     */
    public Object visit(ASTSubtractNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTMulNode, Object)
     */
    public Object visit(ASTMulNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTDivNode, Object)
     */
    public Object visit(ASTDivNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTModNode, Object)
     */
    public Object visit(ASTModNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTNotNode, Object)
     */
    public Object visit(ASTNotNode node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTFloatingPointLiteral, Object)
     */
    public Object visit(ASTFloatingPointLiteral node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTIntegerLiteral, Object)
     * @since 1.5
     */
    public Object visit(ASTIntegerLiteral node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTStringLiteral, Object)
     */
    public Object visit(ASTStringLiteral node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTIdentifier, Object)
     */
    public Object visit(ASTIdentifier node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTMethod, Object)
     */
    public Object visit(ASTMethod node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTReference, Object)
     */
    public Object visit(ASTReference node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTTrue, Object)
     */
    public Object visit(ASTTrue node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTFalse, Object)
     */
    public Object visit(ASTFalse node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTBlock, Object)
     */
    public Object visit(ASTBlock node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTText, Object)
     */
    public Object visit(ASTText node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTIfStatement, Object)
     */
    public Object visit(ASTIfStatement node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTElseStatement, Object)
     */
    public Object visit(ASTElseStatement node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTElseIfStatement, Object)
     */
    public Object visit(ASTElseIfStatement node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTObjectArray, Object)
     */
    public Object visit(ASTObjectArray node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTDirective, Object)
     */
    public Object visit(ASTDirective node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTWord, Object)
     */
    public Object visit(ASTWord node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTSetDirective, Object)
     */
    public Object visit(ASTSetDirective node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTEscapedDirective, Object)
     * @since 1.5
     */
    public Object visit(ASTEscapedDirective node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTEscape, Object)
     * @since 1.5
     */
    public Object visit(ASTEscape node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTMap, Object)
     * @since 1.5
     */
    public Object visit(ASTMap node, Object data) {
        return showNode(node, data);
    }

    /**
     * @see BaseVisitor#visit(ASTIntegerRange, Object)
     */
    public Object visit(ASTIntegerRange node, Object data) {
        return showNode(node, data);
    }
}
