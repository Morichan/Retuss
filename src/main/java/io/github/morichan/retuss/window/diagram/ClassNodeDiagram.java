package io.github.morichan.retuss.window.diagram;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.ClassDiagramDrawer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p> クラス図キャンバスにおけるクラスに関するクラス </p>
 *
 * <p>
 * {@link NodeDiagram} を継承しています。
 * </p>
 */
public class ClassNodeDiagram extends NodeDiagram {
    private Class umlClass = new Class();

    private Point2D topLeftCorner = Point2D.ZERO;
    private Point2D bottomRightCorner = Point2D.ZERO;

    private int classNameFontSize = 20;
    private int classAttributeFontSize = 15;
    private int classOperationFontSize = 15;
    private final double defaultWidth = 100.0;
    private final double defaultClassHeight = 40.0;
    private final double defaultAttributeHeight = 20.0;
    private final double defaultOperationHeight = 20.0;
    private final double classNameSpace = 20.0;
    private final double leftSpace = 5.0;

    private List<ClassDiagramGraphic> attributes = new ArrayList<>();
    private List<RelationshipAttributeGraphic> relations = new ArrayList<>();
    private List<ClassDiagramGraphic> operations = new ArrayList<>();

    private int attributeNotVisibilityCount = 0;

    private int operationNotVisibilityCount = 0;

    /**
     * <p> クラスを抽出します </p>
     *
     * <p>
     *     正確には、 {@link #draw()} メソッド内で抽出しています。
     *     そのため、このメソッドは {@link #draw()} メソッド実行後に実行してください。
     * </p>
     *
     * @return {@link Class} インスタンスに整形したクラス
     */
    public Class extractClass() {
        return umlClass;
    }

    /**
     * <p> クラス名の左右の余白の長さを取得します </p>
     *
     * @return クラス名の左右の余白の長さ
     */
    public double getClassNameSpace() {
        return classNameSpace;
    }

    public List<Attribute> extractRelations() {
        List<Attribute> relations = new ArrayList<>();
        for (RelationshipAttributeGraphic rag : this.relations) {
            relations.add(rag.getAttribute());
        }
        return relations;
    }

    /**
     * <p> クラス図キャンバスにおける任意のポイントに、このクラスを描画しているか否かの真偽値を取得します </p>
     *
     * @param x 任意のポイントのX軸
     * @param y 任意のポイントのY軸
     * @return 任意のポイントにこのクラスを描画しているか否かの真偽値
     */
    @Override
    public boolean isAlreadyDrawnNode(double x, double y) {
        boolean act = false;

        if (topLeftCorner.getX() < x && topLeftCorner.getY() < y
                && x < bottomRightCorner.getX() && y < bottomRightCorner.getY())
            act = true;

        return act;
    }

    /**
     * <p> クラス図キャンバスにおける任意の座標に、このクラスを移動します </p>
     *
     * @param point 任意の座標
     */
    @Override
    public void moveTo(Point2D point) {
        currentPoint = point;
        draw();
    }

    /**
     * <p> クラス図キャンバスにおいてこのクラスを選択している場合は真を返す真偽値を設定します </p>
     *
     * @param isChosen このクラスを選択しているか否かの真偽値
     */
    @Override
    public void setChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    /**
     * <p> クラス図キャンバスにおいてクラスを生成、またはクラスの属性または操作を追加する。 </p>
     *
     * <p>
     * 描画は行いません。
     * </p>
     *
     * @param type クラスのタイトルまたはクラスの属性や操作といった種類
     * @param text クラス名、またはクラスの属性または操作のテキスト
     */
    @Override
    public void createNodeText(ContentType type, String text) {
        if (type == ContentType.Title) {
            nodeText = text;
        } else if (type == ContentType.Attribute) {
            attributes.add(new AttributeGraphic(text));
        } else if (type == ContentType.Operation) {
            operations.add(new OperationGraphic(text));
        } else { // if (type == ContentType.Composition) {
            relations.add(new RelationshipAttributeGraphic(text));
        }
    }

    /**
     * <p> クラス図キャンバスにおいて描画済みのクラス名、またはクラスに追加済みの属性または操作を変更します </p>
     *
     * @param type   クラスのタイトルまたはクラスの内容の種類
     * @param number クラスの属性や操作といった内容の場合はその番号 <br> クラス名の場合この数値は関係ない。
     * @param text   クラス名、またはクラスの属性または操作のテキスト
     */
    @Override
    public void changeNodeText(ContentType type, int number, String text) {
        if (type == ContentType.Title) {
            nodeText = text;
        } else if (type == ContentType.Attribute) {
            attributes.get(number).setText(text);
        } else if (type == ContentType.Operation) {
            operations.get(number).setText(text);
        } else { // if (type == ContentType.Composition) {
            relations.get(number).setText(text);
        }
    }

    /**
     * <p> クラスの属性または操作を削除します </p>
     *
     * <p>
     * クラス自体の削除は、このインスタンスのリストを持つ {@link ClassDiagramDrawer} が行います。
     * </p>
     *
     * @param type   クラスの内容の種類
     * @param number クラスの内容の番号
     */
    @Override
    public void deleteNodeText(ContentType type, int number) {
        if (type == ContentType.Attribute) {
            attributes.remove(number);
        } else if (type == ContentType.Operation) {
            operations.remove(number);
        } else { // if (type == ContentType.Composition) {
            relations.remove(number);
        }
    }

    /**
     * <p> クラスの内容のテキストを取得します </p>
     *
     * @param type   クラスの内容の種類
     * @param number クラスの内容の番号
     * @return クラスの内容のテキスト
     */
    @Override
    public String getNodeContentText(ContentType type, int number) {
        String content;

        if (type == ContentType.Attribute) {
            content = attributes.get(number).getText();
        } else if (type == ContentType.Operation) {
            content = operations.get(number).getText();
        } else if (type == ContentType.Composition) {
            content = relations.get(number).getText();
        } else {
            content = "";
        }
        return content;
    }

    /**
     * <p> クラスの内容のテキストのリストを取得します </p>
     *
     * @param type クラスの内容の種類
     * @return クラスの内容のテキストのリスト
     */
    @Override
    public List<String> getNodeContents(ContentType type) {
        List<String> list = new ArrayList<>();
        if (type == ContentType.Attribute) {
            for (ClassDiagramGraphic attribute : attributes) {
                list.add(attribute.getText());
            }
        } else if (type == ContentType.Operation) {
            for (ClassDiagramGraphic operation : operations) {
                list.add(operation.getText());
            }
        } else if (type == ContentType.Composition) {
            for (RelationshipAttributeGraphic relation : relations) {
                list.add(relation.getText());
            }
        } else {
            list = null;
        }
        return list;
    }

    /**
     * <p> クラスの任意の内容における任意のサブ内容に真偽値を設定します </p>
     *
     * <p>
     * 正確には、クラスの任意の内容の種類 {@code type} における番号 {@code contentNumber} の任意のサブ内容の種類 {@code subtype} に真偽値 {@code isChecked} を設定します。
     * 例えば、クラス図の属性の表示しているか否かを設定する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れます。
     * </p>
     *
     * @param type          クラスの内容の種類
     * @param subtype       クラスのサブ内容の種類
     * @param contentNumber クラスの内容の番号
     * @param isChecked     クラスの内容の真偽値
     */
    @Override
    public void setNodeContentBoolean(ContentType type, ContentType subtype, int contentNumber, boolean isChecked) {
        if (type == ContentType.Attribute) {
            if (subtype == ContentType.Indication) {
                attributes.get(contentNumber).setIndication(isChecked);
            }
        } else if (type == ContentType.Operation) {
            if (subtype == ContentType.Indication) {
                operations.get(contentNumber).setIndication(isChecked);
            }
        } else { // if (type == ContentType.Composition) {
            if (subtype == ContentType.Indication) {
                relations.get(contentNumber).setIndication(isChecked);
            }
        }
    }

    /**
     * <p> クラスの任意の内容における任意のサブ内容の真偽値のリストを取得します </p>
     *
     * <p>
     * 例えば、クラス図の属性の表示しているか否かのリストを取得する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れます。
     * </p>
     *
     * @param type    クラスの内容の種類
     * @param subtype クラスのサブ内容の種類
     * @return クラスの任意の種類の任意の内容の真偽値のリスト
     */
    @Override
    public List<Boolean> getNodeContentsBoolean(ContentType type, ContentType subtype) {
        List<Boolean> list = new ArrayList<>();
        if (type == ContentType.Attribute) {
            for (ClassDiagramGraphic attribute : attributes) {
                list.add(attribute.isIndicate());
            }
        } else if (type == ContentType.Operation) {
            for (ClassDiagramGraphic operation : operations) {
                list.add(operation.isIndicate());
            }
        } else if (type == ContentType.Composition) {
            for (ClassDiagramGraphic relation : relations) {
                list.add(relation.isIndicate());
            }
        } else {
            list = null;
        }
        return list;
    }

    /**
     * <p> クラス図キャンバスにおいてクラスを描画します </p>
     *
     * <p>
     * 実際にクラス図キャンバスにおいて描画するのは {@link ClassNodeDiagram#drawGraphicsContext(Text, List, List, double, double, double, double, double)} で行います。
     * </p>
     * <p>
     * {@link NodeDiagram#gc} が存在しない場合は {@link NullPointerException} を返す。
     */
    @Override
    public void draw() {
        if (nodeText.length() <= 0) return;

        Text classNameText = new Text(nodeText);
        classNameText.setFont(Font.font(diagramFont, FontWeight.BOLD, classNameFontSize));
        umlClass = new Class(nodeText);

        List<Text> attributesText = new ArrayList<>();
        for (ClassDiagramGraphic attribute : attributes) {
            Text text = new Text(attribute.getText());
            text.setFont(Font.font(diagramFont, FontWeight.LIGHT, classAttributeFontSize));
            attributesText.add(text);
            umlClass.addAttribute(((AttributeGraphic) attribute).getAttribute());
        }

        List<Text> operationsText = new ArrayList<>();
        for (ClassDiagramGraphic operation : operations) {
            Text text = new Text(operation.getText());
            text.setFont(Font.font(diagramFont, FontWeight.LIGHT, classOperationFontSize));
            operationsText.add(text);
            umlClass.addOperation(((OperationGraphic) operation).getOperation());
        }

        double maxWidth = calculateMaxWidth(classNameText, attributesText, operationsText);
        double classHeight = defaultClassHeight;
        double attributeHeight = calculateMaxAttributeHeight(attributes);
        double operationHeight = calculateMaxOperationHeight(operations);
        double operationStartHeight = calculateStartOperationHeight(attributes);

        calculateWidthAndHeight(maxWidth, classHeight + attributeHeight + operationHeight);

        drawGraphicsContext(classNameText, attributesText, operationsText, maxWidth, classHeight, attributeHeight, operationHeight, operationStartHeight);
    }

    /**
     * <p> クラス図キャンバスにおいてクラスを描画します </p>
     *
     * @param classNameText        クラス名のテキスト
     * @param attributesText       クラス属性のテキストのリスト
     * @param operationsText       クラス操作のテキストのリスト
     * @param maxWidth             最大幅
     * @param classHeight          クラス名の高さ
     * @param attributeHeight      クラス属性の高さ
     * @param operationHeight      クラス操作の高さ
     * @param operationStartHeight クラス捜査を最初に描画する高さ
     */
    private void drawGraphicsContext(Text classNameText, List<Text> attributesText, List<Text> operationsText, double maxWidth, double classHeight, double attributeHeight, double operationHeight, double operationStartHeight) {
        gc.setFill(Color.BEIGE);
        gc.fillRect(topLeftCorner.getX(), topLeftCorner.getY(), maxWidth, classHeight + attributeHeight + operationHeight);

        if (isChosen) {
            gc.setStroke(Color.RED);
        } else {
            gc.setStroke(Color.BLACK);
        }
        gc.strokeRect(topLeftCorner.getX(), topLeftCorner.getY(), maxWidth, classHeight + attributeHeight + operationHeight);
        gc.strokeLine(topLeftCorner.getX(), topLeftCorner.getY() + classHeight, bottomRightCorner.getX(), topLeftCorner.getY() + classHeight);
        gc.strokeLine(topLeftCorner.getX(), topLeftCorner.getY() + classHeight + attributeHeight, bottomRightCorner.getX(), topLeftCorner.getY() + classHeight + attributeHeight);

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(classNameText.getFont());
        gc.fillText(classNameText.getText(), currentPoint.getX(), topLeftCorner.getY() + classHeight / 2);

        if (attributesText.size() > 0) {
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setFont(attributesText.get(0).getFont());
            int notDrawAttributeCount = 0;
            boolean isExistedNoIndication = false;
            for (int i = 0; i < attributesText.size(); i++) {
                if (attributes.get(i).isIndicate()) {
                    gc.fillText(attributesText.get(i).getText(),
                            topLeftCorner.getX() + leftSpace, topLeftCorner.getY() + classHeight + 15.0 + (defaultAttributeHeight * (i - notDrawAttributeCount)));
                } else {
                    notDrawAttributeCount++;
                    isExistedNoIndication = true;
                }
            }
            if (isExistedNoIndication) {
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText("... " + attributeNotVisibilityCount + " more",
                        currentPoint.getX(), topLeftCorner.getY() + classHeight + 15.0 + (defaultAttributeHeight * (attributesText.size() - attributeNotVisibilityCount)));
            }
        }

        if (operationsText.size() > 0) {
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setFont(operationsText.get(0).getFont());
            int notDrawOperationCount = 0;
            boolean isExistedNoIndication = false;
            for (int i = 0; i < operationsText.size(); i++) {
                if (operations.get(i).isIndicate()) {
                    gc.fillText(operationsText.get(i).getText(),
                            topLeftCorner.getX() + leftSpace, topLeftCorner.getY() + classHeight + 15.0 + (defaultOperationHeight * (i - notDrawOperationCount)) + operationStartHeight);
                } else {
                    notDrawOperationCount++;
                    isExistedNoIndication = true;
                }
            }
            if (isExistedNoIndication) {
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText("... " + operationNotVisibilityCount + " more",
                        currentPoint.getX(), topLeftCorner.getY() + classHeight + 15.0 + (defaultOperationHeight * (operationsText.size() - operationNotVisibilityCount)) + operationStartHeight);
            }
        }
    }

    /**
     * <p> クラス図キャンバスにおいて描画するクラスの最大幅を算出します </p>
     * <p>
     * クラス名、クラス属性、クラス操作の文字列が長い場合は最大幅を広げる必要があります。
     * クラス名とクラス属性とクラス属性は全て {@link Text} クラスのインスタンスとして取得するため、そこから得られる
     * {@link javafx.geometry.Bounds} クラスの {@link Bounds#getWidth()} メソッドに依存します。
     * </p>
     *
     * <p>
     * 全てのクラス内のテキストがあまり広い必要がない場合は、 {@link ClassNodeDiagram#defaultWidth} を返します。
     * </p>
     *
     * @param text           クラス名
     * @param attributesText クラス属性のリスト
     * @param operationsText クラス操作のリスト
     * @return 最大幅
     */
    public double calculateMaxWidth(Text text, List<Text> attributesText, List<Text> operationsText) {
        double width = defaultWidth - classNameSpace;

        List<Double> classAttributes = new ArrayList<>();
        classAttributes.add(0.0);
        for (int i = 0; i < attributesText.size(); i++) {
            if (attributes.get(i).isIndicate())
                classAttributes.add(attributesText.get(i).getLayoutBounds().getWidth());
        }
        List<Double> classOperations = new ArrayList<>();
        classOperations.add(0.0);
        for (int i = 0; i < operationsText.size(); i++) {
            if (operations.get(i).isIndicate())
                classOperations.add(operationsText.get(i).getLayoutBounds().getWidth());
        }

        classAttributes.sort(Comparator.reverseOrder());
        classOperations.sort(Comparator.reverseOrder());

        List<Double> classWidth = Arrays.asList(text.getLayoutBounds().getWidth(), classAttributes.get(0), classOperations.get(0));

        classWidth.sort(Comparator.reverseOrder());

        if (width < classWidth.get(0)) width = classWidth.get(0) + classNameSpace;
        else width = defaultWidth;

        return width;
    }

    /**
     * <p> クラス図キャンバスにおいて描画するクラス属性の箇所の高さを算出します </p>
     *
     * <p>
     * クラス属性が複数ある場合は最大高さを高くしなければなりません。
     * ただし、1つ以上非表示のクラス属性が存在する場合は、高さを {@code (非表示の属性の個数 - 1) * defaultAttributeHeight} 分減らします。
     * ここにおける {@code -1} とは、非表示の属性の個数を表示する列の分です。
     * クラス属性が存在しない場合、またはクラス属性が1つだけ存在する場合、または全てのクラス属性が非表示の場合は {@link ClassNodeDiagram#defaultAttributeHeight} を返します。
     * </p>
     *
     * @param attributes クラス属性のリスト
     * @return クラス属性の箇所の高さ
     */
    public double calculateMaxAttributeHeight(List<ClassDiagramGraphic> attributes) {
        double height = defaultAttributeHeight;
        attributeNotVisibilityCount = countNotBooleanContents(attributes);

        if (attributes.size() > 0) {
            if (attributeNotVisibilityCount > 0) {
                if (attributeNotVisibilityCount != attributes.size())
                    height = (attributes.size() - attributeNotVisibilityCount + 1) * defaultAttributeHeight;
                else
                    height = defaultAttributeHeight;
            } else {
                height = attributes.size() * defaultAttributeHeight;
            }
        }

        return height;
    }

    /**
     * <p> クラス操作を描画開始する高さを計算します </p>
     *
     * <p>
     * ここにおける高さの基準とは、クラス図キャンバスにおいてクラス属性を描画開始する高さです。
     * すなわち、 {@code 0.0} の場合、クラス属性の描画開始する高さを返すことになります（実際には必ず属性1つ分の高さは存在します）。
     * </p>
     *
     * @param attributes クラス属性のリスト
     * @return クラス操作を描画開始する高さ
     */
    public double calculateStartOperationHeight(List<ClassDiagramGraphic> attributes) {
        double height = 20.0;

        if (attributes.size() > 0) {
            height = attributes.size() * 20.0;
        }
        if (attributeNotVisibilityCount > 0) {
            height -= ((attributeNotVisibilityCount - 1) * 20);
        }
        if (attributes.size() == attributeNotVisibilityCount) {
            height = 20.0;
        }

        return height;
    }

    /**
     * <p> クラス図キャンバスにおいて描画するクラス操作の箇所の高さを算出します </p>
     *
     * <p>
     * クラス操作が複数ある場合は最大高さを高くする必要があります。
     * ただし、1つ以上非表示のクラス操作が存在する場合は、高さを {@code (非表示の操作の個数 - 1) * defaultOperationHeight} 分減らします。
     * ここにおける {@code -1} とは、非表示の操作の個数を表示する列の分です。
     * クラス操作が存在しない場合、またはクラス操作が1つだけ存在する場合、または全てのクラス操作が非表示の場合は {@link ClassNodeDiagram#defaultOperationHeight} を返します。
     * </p>
     *
     * @param operations クラス操作のリスト
     * @return クラス操作の箇所の高さ
     */
    public double calculateMaxOperationHeight(List<ClassDiagramGraphic> operations) {
        double height = defaultOperationHeight;
        operationNotVisibilityCount = countNotBooleanContents(operations);

        if (operations.size() > 0) {
            if (operationNotVisibilityCount > 0) {
                if (operationNotVisibilityCount != operations.size())
                    height = (operations.size() - operationNotVisibilityCount + 1) * defaultOperationHeight;
                else
                    height = defaultOperationHeight;
            } else {
                height = operations.size() * defaultOperationHeight;
            }
        }

        return height;
    }

    /**
     * <p> 描画しないクラス属性または操作の数をカウントします </p>
     *
     * @param data クラス属性または操作
     * @return 描画しないクラス属性または操作の数
     */
    public int countNotBooleanContents(List<ClassDiagramGraphic> data) {
        int count = 0;
        for (ClassDiagramGraphic datum : data) {
            if (!datum.isIndicate()) count++;
        }
        return count;
    }

    /**
     * <p> クラスの高さと幅を計算します </p>
     *
     * <p>
     * 同時に、クラスの左上角のポイントとクラスの右下角のポイントを計算します。
     * </p>
     *
     * @param maxWidth  クラスの最大幅
     * @param maxHeight クラスの最大高さ
     */
    public void calculateWidthAndHeight(double maxWidth, double maxHeight) {
        calculateUpperLeftCorner(currentPoint, maxWidth, maxHeight);
        calculateBottomRightCorner(currentPoint, maxWidth, maxHeight);
        width = bottomRightCorner.subtract(topLeftCorner).getX();
        height = bottomRightCorner.subtract(topLeftCorner).getY();
    }

    /**
     * <p> クラスの左上角のポイントを計算します </p>
     *
     * <p>
     * クラスの中心点から最大幅の半分X軸のマイナス方向に、最大高さの半分Y軸のマイナス方向に移動した点です。
     * </p>
     *
     * @param point  クラスの中心点
     * @param width  クラスの最大幅
     * @param height クラスの最大高さ
     */
    private void calculateUpperLeftCorner(Point2D point, double width, double height) {
        topLeftCorner = new Point2D(point.getX() - width / 2, point.getY() - height / 2);
    }

    /**
     * <p> クラスの右下角のポイントを計算します </p>
     *
     * <p>
     * クラスの中心点から最大幅の半分X軸のプラス方向に、最大高さの半分Y軸のプラス方向に移動した点です。
     * </p>
     *
     * @param point  クラスの中心点
     * @param width  クラスの最大幅
     * @param height クラスの最大高さ
     */
    private void calculateBottomRightCorner(Point2D point, double width, double height) {
        bottomRightCorner = new Point2D(point.getX() + width / 2, point.getY() + height / 2);
    }
}
