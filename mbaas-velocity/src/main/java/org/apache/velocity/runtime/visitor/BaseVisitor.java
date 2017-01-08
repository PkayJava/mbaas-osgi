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

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.node.*;

import java.io.Writer;

/**
 * This is the base class for all visitors.
 * For each AST node, this class will provide
 * a bare-bones method for traversal.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: BaseVisitor.java 747106 2009-02-23 19:25:14Z nbubna $
 */
public abstract class BaseVisitor implements ParserVisitor {
    /**
     * Context used during traversal
     */
    protected InternalContextAdapter context;

    /**
     * Writer used as the output sink
     */
    protected Writer writer;

    /**
     * @param writer
     */
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * @param context
     */
    public void setContext(InternalContextAdapter context) {
        this.context = context;
    }

    /**
     * @see ParserVisitor#visit(SimpleNode, Object)
     */
    public Object visit(SimpleNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTprocess, Object)
     */
    public Object visit(ASTprocess node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTExpression, Object)
     */
    public Object visit(ASTExpression node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTAssignment, Object)
     */
    public Object visit(ASTAssignment node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTOrNode, Object)
     */
    public Object visit(ASTOrNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTAndNode, Object)
     */
    public Object visit(ASTAndNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTEQNode, Object)
     */
    public Object visit(ASTEQNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTNENode, Object)
     */
    public Object visit(ASTNENode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTLTNode, Object)
     */
    public Object visit(ASTLTNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTGTNode, Object)
     */
    public Object visit(ASTGTNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTLENode, Object)
     */
    public Object visit(ASTLENode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTGENode, Object)
     */
    public Object visit(ASTGENode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTAddNode, Object)
     */
    public Object visit(ASTAddNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTSubtractNode, Object)
     */
    public Object visit(ASTSubtractNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTMulNode, Object)
     */
    public Object visit(ASTMulNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTDivNode, Object)
     */
    public Object visit(ASTDivNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTModNode, Object)
     */
    public Object visit(ASTModNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTNotNode, Object)
     */
    public Object visit(ASTNotNode node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTIntegerLiteral, Object)
     */
    public Object visit(ASTIntegerLiteral node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTFloatingPointLiteral, Object)
     * @since 1.5
     */
    public Object visit(ASTFloatingPointLiteral node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTStringLiteral, Object)
     */
    public Object visit(ASTStringLiteral node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTIdentifier, Object)
     */
    public Object visit(ASTIdentifier node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTMethod, Object)
     */
    public Object visit(ASTMethod node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTReference, Object)
     */
    public Object visit(ASTReference node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTTrue, Object)
     */
    public Object visit(ASTTrue node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTFalse, Object)
     */
    public Object visit(ASTFalse node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTBlock, Object)
     */
    public Object visit(ASTBlock node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTText, Object)
     */
    public Object visit(ASTText node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTIfStatement, Object)
     */
    public Object visit(ASTIfStatement node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTElseStatement, Object)
     */
    public Object visit(ASTElseStatement node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTElseIfStatement, Object)
     */
    public Object visit(ASTElseIfStatement node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTComment, Object)
     */
    public Object visit(ASTComment node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTObjectArray, Object)
     */
    public Object visit(ASTObjectArray node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTWord, Object)
     */
    public Object visit(ASTWord node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTSetDirective, Object)
     */
    public Object visit(ASTSetDirective node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTDirective, Object)
     */
    public Object visit(ASTDirective node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTEscapedDirective, Object)
     * @since 1.5
     */
    public Object visit(ASTEscapedDirective node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTEscape, Object)
     * @since 1.5
     */
    public Object visit(ASTEscape node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTMap, Object)
     * @since 1.5
     */
    public Object visit(ASTMap node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * @see ParserVisitor#visit(ASTIntegerRange, Object)
     * @since 1.5
     */
    public Object visit(ASTIntegerRange node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }
}
