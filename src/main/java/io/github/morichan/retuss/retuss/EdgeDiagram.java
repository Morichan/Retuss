package io.github.morichan.retuss.retuss;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdgeDiagram {
    GraphicsContext gc;
    private List<RelationshipAttribute> relations = new ArrayList<>();
    private boolean hasRelationSourceNodeSelected = false;
    private UtilityJavaFXComponent util = new UtilityJavaFXComponent();

    /**
     * {@link ClassDiagramDrawer} クラスから {@link javafx.scene.canvas.Canvas} クラスのインスタンスが持つ {@link GraphicsContext} クラスのインスタンスを受け取る。
     * 受け取るのは最初にインスタンスを生成した時である。
     *
     * @param gc {@link Controller} クラスから受け取るグラフィックスコンテキスト
     */
    public void setGraphicsContext( GraphicsContext gc ) {
        this.gc = gc;
    }

    /**
     * 関係を生成する。
     *
     * @param type 関係の種類
     * @param text 関係の名前
     */
    public void createEdgeText( ContentType type, String text ) {
        if( text.length() > 0 || type == ContentType.Generalization ) {
            relations.add( new RelationshipAttribute( text ) );
            relations.get( relations.size() - 1 ).setType( type );
        }
    }

    /**
     * 関係を変更する。
     *
     * @param type 関係の種類
     * @param number 変更する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param text 変更後の関係の名前
     */
    public void changeEdgeText( ContentType type, int number, String text ) {
        if( text.length() > 0 ) {
            relations.get( number ).setName( text );
        }
    }

    /**
     * 関係を削除する。
     *
     * @param type 関係の種類
     * @param number 削除する関係の番号 ここにおける番号とは、生成した順番を表す。
     */
    public void deleteEdgeText( ContentType type, int number ) {
        relations.remove( number );
    }

    /**
     * 関係の内容のテキストを取得する。
     *
     * @param type 関係の種類
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係の内容のテキスト 存在しなかった場合は空文字を返す。
     */
    public String getEdgeContentText( ContentType type, int number ) {
        if( relations.size() > 0 ) {
            return relations.get( number ).getName();
        } else {
            return "";
        }
    }

    /**
     * 関係先のノードのIDを設定する。
     *
     * @param type 関係の種類
     * @param number 設定する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param id 設定するID
     */
    public void setRelationId( ContentType type, int number, int id ) {
        relations.get( number ).setRelationId( id );
    }

    /**
     * 関係先のノードのIDを取得する。
     *
     * @param type 関係の種類
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係先のID
     */
    public int getRelationId( ContentType type, int number ) {
        return relations.get( number ).getRelationId();
    }

    /**
     * 関係元のノードのIDを設定する。
     *
     * @param type 関係の種類
     * @param number 設定する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param id 設定するID
     */
    public void setRelationSourceId( ContentType type, int number, int id ) {
        relations.get( number ).setRelationSourceId( id );
    }

    /**
     * 関係元のノードのIDを取得する。
     *
     * @param type 関係の種類
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係元のID
     */
    public int getRelationSourceId( ContentType type, int number ) {
        return relations.get( number ).getRelationSourceId();
    }

    /**
     * 関係先のノードのポイントを設定する。
     *
     * @param type 関係の種類
     * @param number 設定する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param point 関係先のノードのポイント 関係を描画する最終点であるため、関係先のノードの中心点が好ましい。
     */
    public void setRelationPoint( ContentType type, int number, Point2D point ) {
        relations.get( number ).setRelationPoint( point );
    }

    /**
     * 関係先のノードのポイントを取得する。
     *
     * @param type 関係の種類
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係先のポイント
     */
    public Point2D getRelationPoint( ContentType type, int number ) {
        return relations.get( number ).getRelationPoint();
    }

    /**
     * 関係元のノードのポイントを設定する。
     *
     * @param type 関係の種類
     * @param number 設定する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param point 関係元のノードのポイント 関係を描画する開始点であるため、関係元のノードの中心点が好ましい。
     */
    public void setRelationSourcePoint( ContentType type, int number, Point2D point ) {
        relations.get( number ).setRelationSourcePoint( point );
    }

    /**
     * 関係元のノードのポイントを取得する。
     *
     * @param type 関係の種類
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係元のポイント
     */
    public Point2D getRelationSourcePoint( ContentType type, int number ) {
        return relations.get( number ).getRelationSourcePoint();
    }

    /**
     * 関係の種類を取得する。
     *
     * @param number 取得する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @return 関係の種類
     */
    public ContentType getContentType( int number ) {
        return relations.get( number ).getType();
    }

    /**
     * クラス図キャンバスにおいて描画している関係の数を取得する。
     *
     * @return クラス図キャンバスにおいて描画している関係の数
     */
    public int getCompositionsCount() {
        return relations.size();
    }

    /**
     * 関係元のノードを選択している場合は真を返す。
     *
     * @return 関係元のノードを選択しているか否かの真偽値
     */
    boolean hasRelationSourceNodeSelected() {
        return hasRelationSourceNodeSelected;
    }

    /**
     * 関係元のノードを選択しているか否かの真偽値を反転する。
     */
    public void changeRelationSourceNodeSelectedState() {
        hasRelationSourceNodeSelected = ! hasRelationSourceNodeSelected;
    }

    /**
     * 関係元のノードを選択しているか否かの真偽値を偽にリセットする。
     */
    public void resetRelationSourceNodeSelectedState() {
        hasRelationSourceNodeSelected = false;
    }

    /**
     * クラス図キャンバスにおける任意のポイントに描画している関係を取得する。
     * 任意のポイントに関係を何も描画していない場合は {@code null} を返す。
     *
     * @param mousePoint クラス図キャンバスにおける任意のポイント
     * @return 任意のポイントに描画している関係
     */
    public RelationshipAttribute searchCurrentRelation(Point2D mousePoint ) {
        RelationshipAttribute content = null;
        int number = searchCurrentRelationNumber( mousePoint );
        if( number > -1 ) content = relations.get( number );
        return content;
    }

    /**
     * 関係元に元々存在する全ての汎化関係を削除する。
     * 多重継承を防ぐために、汎化関係を新しく生成した後に用いる。
     *
     * @param id 関係元のID
     */
    public void deleteGeneralizationFromSameRelationSourceNode( int id ) {
        // 最後に設定した汎化関係は無視
        for( int i = 0; i < relations.size() - 1; i++ ) {
            if( relations.get( i ).getRelationSourceId() == id ) {
                relations.remove( i );
                i--;
            }
        }
    }

    /**
     * クラス図キャンバスにおける任意のポイントに描画している関係を変更する。
     *
     * @param mousePoint クラス図キャンバスにおける任意のポイント
     * @param content 変更する内容のテキスト
     */
    public void changeCurrentRelation( Point2D mousePoint, String content ) {
        int number = searchCurrentRelationNumber( mousePoint );
        if( number > -1 ) changeEdgeText( relations.get( number ).getType(), number, content );
    }

    /**
     * クラス図キャンバスにおける任意のポイントに描画している関係を削除する。
     *
     * @param mousePoint クラス図キャンバスにおける任意のポイント
     */
    public void deleteCurrentRelation( Point2D mousePoint ) {
        int number = searchCurrentRelationNumber( mousePoint );
        if( number > -1 ) deleteEdgeText( relations.get( number ).getType(), number );
    }

    /**
     * クラス図キャンバスにおける任意のポイントに描画している関係の番号を探索する。
     * 重なって描画している箇所を設定した場合は、一番上（最後）に描画した関係の番号を返す。
     * ここにおける番号とは、生成した順番を表す。
     *
     * @param mousePoint クラス図キャンバスにおける任意のポイント
     * @return 関係の番号
     */
    public int searchCurrentRelationNumber( Point2D mousePoint ) {
        int number = -1;

        // 重なっているエッジの内1番上に描画しているエッジはcompositionsリストの1番後半に存在するため、1番上に描画しているエッジを取るためには尻尾から見なければならない。
        for(int i = relations.size() - 1; i >= 0; i-- ) {
            if( isAlreadyDrawnAnyEdge( relations.get( i ).getType(), i, mousePoint ) ) {
                number = i;
                break;
            }
        }

        return number;
    }

    /**
     * クラス図キャンバスにおける任意のポイントに、任意の番号の関係を描画済みの場合は真を返す。
     *
     * @param type 関係の種類
     * @param number 判定する関係の番号 ここにおける番号とは、生成した順番を表す。
     * @param mousePoint クラス図キャンバスにおける任意のポイント
     * @return 描画済みか否かの真偽値
     */
    public boolean isAlreadyDrawnAnyEdge( ContentType type, int number, Point2D mousePoint ) {
        boolean isAlreadyDrawnAnyEdge = false;
        Point2D relationPoint = relations.get( number ).getRelationPoint();
        Point2D relationSourcePoint = relations.get( number ).getRelationSourcePoint();

        List< Point2D > edgePolygon = createOneEdgeQuadrangleWithMargin( getRelationMarginLength( ContentType.Composition ), relationPoint, relationSourcePoint );
        if( util.isInsidePointFromPolygonUsingWNA( edgePolygon, mousePoint ) ) isAlreadyDrawnAnyEdge = true;

        return isAlreadyDrawnAnyEdge;
    }

    /**
     * <p> 関係の開始点と終了点から、長さ {@code margin} 離れた4点のリストを返す。 </p>
     *
     * <p>
     *     具体的な例を下図に示す。 <br><br>
     *     3----s----0 <br>
     *          |      <br>
     *          |      <br>
     *          |      <br>
     *          |      <br>
     *          |      <br>
     *     2----e----1
     * </p>
     *
     * <ul>
     *     <li> s--e : 関係の開始点と終了点、および関係 </li>
     *     <li> n--s, n--e : 関係の開始点および終了点と長さ {@code margin} 離れた点n（nは数値であり、戻り値のリストの順番を表す。） </li>
     * </ul>
     *
     * @param margin 関係の開始点および終了点からの距離
     * @param startEdge 関係の開始点
     * @param endEdge 関係の終了点
     * @return 長さ {@code margin} 離れた4点のリスト
     */
    public List< Point2D > createOneEdgeQuadrangleWithMargin( double margin, Point2D startEdge, Point2D endEdge ) {
        List< Point2D > edgePolygon = new ArrayList<>();

        // 傾き
        double inclination = calculateNormalLineInclination( startEdge, endEdge );
        int sign = 1;

        if( Double.isInfinite( inclination ) ) {
            if( startEdge.getX() > endEdge.getX() ) sign = -1;
            edgePolygon.add( new Point2D( startEdge.getX(), startEdge.getY() - margin * sign ) );
            edgePolygon.add( new Point2D( endEdge.getX(), endEdge.getY() - margin * sign ) );
            edgePolygon.add( new Point2D( endEdge.getX(), endEdge.getY() + margin * sign ) );
            edgePolygon.add( new Point2D( startEdge.getX(), startEdge.getY() + margin * sign ) );

        } else if( inclination == 0.0 ) {
            if( startEdge.getY() < endEdge.getY() ) sign = -1;
            edgePolygon.add( new Point2D( startEdge.getX() - margin * sign, startEdge.getY() ) );
            edgePolygon.add( new Point2D( endEdge.getX() - margin * sign, endEdge.getY() ) );
            edgePolygon.add( new Point2D( endEdge.getX() + margin * sign, endEdge.getY() ) );
            edgePolygon.add( new Point2D( startEdge.getX() + margin * sign, startEdge.getY() ) );

        } else {
            // 切片
            double intercept = startEdge.getY() / ( inclination * startEdge.getX() );
            double radian = Math.atan( inclination );
            double xAxisLength = margin * Math.cos( radian );
            double yAxisLength = inclination * xAxisLength;

            // ( 切片が0未満 xor 終点のX軸が始点のX軸より大きい )
            if( ( intercept < 0 && startEdge.getX() < endEdge.getX() ) ||
                    ( ! (intercept < 0) && ! (startEdge.getX() < endEdge.getX() ) ) ) sign = -1;

            edgePolygon.add( new Point2D( startEdge.getX() + xAxisLength * sign, startEdge.getY() - yAxisLength * sign ) );
            edgePolygon.add( new Point2D( endEdge.getX() + xAxisLength * sign, endEdge.getY() - yAxisLength * sign ) );
            edgePolygon.add( new Point2D( endEdge.getX() - xAxisLength * sign, endEdge.getY() + yAxisLength * sign ) );
            edgePolygon.add( new Point2D( startEdge.getX() - xAxisLength * sign, startEdge.getY() + yAxisLength * sign ) );
        }

        return edgePolygon;
    }

    /**
     * 任意の番号の関係を描画する。
     * 描画する前に、関係先と関係元のノードの幅および高さから、関係先および関係元と関係のぶつかる交点を取得する。
     *
     * @param relationWidth 関係先のノードの幅
     * @param relationHeight 関係先のノードの高さ
     * @param relationSourceWidth 関係元のノードの幅
     * @param relationSourceHeight 関係元のノードの高さ
     * @param number 描画する関係の番号 ここにおける番号とは、生成した順番を表す。
     */
    public void draw( double relationWidth, double relationHeight, double relationSourceWidth, double relationSourceHeight, int number ) {
        Point2D relationPoint = getRelationPoint( ContentType.Composition, number );
        Point2D relationSourcePoint = getRelationSourcePoint( ContentType.Composition, number );
        Point2D relationIntersectPoint = calculateIntersectionPointLineAndEndNodeSide( relationSourcePoint, relationPoint, relationWidth, relationHeight );
        Point2D relationSourceIntersectPoint = calculateIntersectionPointLineAndEndNodeSide( relationPoint, relationSourcePoint, relationSourceWidth, relationSourceHeight );

        drawEdge( relationIntersectPoint, relationSourceIntersectPoint, number );
    }

    /**
     * <p> 任意の番号の関係を描画する。 </p>
     *
     * <p>
     *     描画する順番を次に示す。
     *     <ol>
     *         <li> 関係の直線 </li>
     *         <li> 関係の先端 </li>
     *         <li> 関係の内容のテキスト </li>
     *     </ol>
     * </p>
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     * @param number 描画する関係の番号 ここにおける番号とは、生成した順番を表す。
     */
    private void drawEdge( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint, int number ) {
        drawLine( relationIntersectPoint, relationSourceIntersectPoint );
        drawEdgeUmbrella( relationIntersectPoint, relationSourceIntersectPoint, number );
        drawEdgeName( relationIntersectPoint, relationSourceIntersectPoint, number );
    }

    /**
     * 関係の直線を描画する。
     *
     * @param relationPoint 直線の始点
     * @param relationSourcePoint 直線の終点
     */
    private void drawLine( Point2D relationPoint, Point2D relationSourcePoint ) {
        gc.setStroke( Color.BLACK );
        gc.strokeLine( relationSourcePoint.getX(), relationSourcePoint.getY(), relationPoint.getX(), relationPoint.getY() );
    }

    /**
     * 任意の番号の関係の先端を描画する。
     * 関係の種類によって描画する先端の種類が変わる。
     * また、複数描画すること場合がある。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     * @param number 描画する関係の番号 ここにおける番号とは、生成した順番を表す。
     */
    private void drawEdgeUmbrella( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint, int number ) {
        ContentType type = getContentType( number );

        if( type == ContentType.Composition ) {
            drawEdgeNavigation( relationIntersectPoint, relationSourceIntersectPoint );
            drawEdgeComposition( relationIntersectPoint, relationSourceIntersectPoint );
        } else if( type == ContentType.Generalization ) {
            drawEdgeGeneralization( relationIntersectPoint, relationSourceIntersectPoint );
        }
    }

    /**
     * 任意の番号の関係の内容のテキストを描画する。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     * @param number 描画する関係の番号 ここにおける番号とは、生成した順番を表す。
     */
    private void drawEdgeName( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint, int number ) {
        double angle = calculateDegreeFromStart( relationIntersectPoint, relationSourceIntersectPoint );
        double length = 20.0;
        Point2D fontPoint = calculateUmbrellaPoint( relationIntersectPoint, angle - 20.0, length );
        Text text = new Text( getEdgeContentText( ContentType.Composition, number ) );
        text.setFont( Font.font( "Consolas" , FontWeight.LIGHT, 15 ) );
        gc.setFill( Color.BLACK );
        gc.setFont( text.getFont() );
        gc.setTextAlign( TextAlignment.LEFT );
        gc.fillText( text.getText(), fontPoint.getX(), fontPoint.getY() );
    }

    /**
     * 関係先への矢印を描画する。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     */
    private void drawEdgeNavigation( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint ) {
        double angle = calculateDegreeFromStart( relationIntersectPoint, relationSourceIntersectPoint );
        double length = 20.0;
        Point2D firstNavigationPoint = calculateUmbrellaPoint( relationIntersectPoint, angle - 20.0, length );
        Point2D secondNavigationPoint = calculateUmbrellaPoint( relationIntersectPoint, angle + 20.0, length );

        gc.strokeLine( relationIntersectPoint.getX(), relationIntersectPoint.getY(), firstNavigationPoint.getX(), firstNavigationPoint.getY() );
        gc.strokeLine( relationIntersectPoint.getX(), relationIntersectPoint.getY(), secondNavigationPoint.getX(), secondNavigationPoint.getY() );
    }

    /**
     * 関係先への汎化を描画する。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     */
    private void drawEdgeGeneralization( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint ) {
        double angle = calculateDegreeFromStart( relationIntersectPoint, relationSourceIntersectPoint );
        double length = 20.0;
        Point2D firstNavigationPoint = calculateUmbrellaPoint( relationIntersectPoint, angle - 30.0, length );
        Point2D secondNavigationPoint = calculateUmbrellaPoint( relationIntersectPoint, angle + 30.0, length );
        List< Point2D > generalizationPoints = Arrays.asList( relationIntersectPoint, firstNavigationPoint, secondNavigationPoint, relationIntersectPoint );

        double[] xPoints = new double[ 4 ];
        double[] yPoints = new double[ 4 ];

        for( int i = 0; i < generalizationPoints.size(); i++ ) {
            xPoints[ i ] = generalizationPoints.get( i ).getX();
            yPoints[ i ] = generalizationPoints.get( i ).getY();
        }

        gc.setFill( Color.WHITE );
        gc.fillPolygon( xPoints, yPoints, 4 );
        gc.setStroke( Color.BLACK );
        gc.strokePolygon( xPoints, yPoints, 4 );
    }

    /**
     * 関係元からのコンポジションを描画する。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     */
    private void drawEdgeComposition( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint ) {
        List< Point2D > compositionPoints = calculateEdgeCompositionPoints( relationIntersectPoint, relationSourceIntersectPoint );
        double[] xPoints = new double[ 5 ];
        double[] yPoints = new double[ 5 ];

        for( int i = 0; i < compositionPoints.size(); i++ ) {
            xPoints[ i ] = compositionPoints.get( i ).getX();
            yPoints[ i ] = compositionPoints.get( i ).getY();
        }

        gc.setFill( Color.BLACK );
        gc.fillPolygon( xPoints, yPoints, 5 );
        gc.setStroke( Color.BLACK );
        gc.strokePolygon( xPoints, yPoints, 5 );
    }

    /**
     * 関係元からのコンポジションを表す黒抜きの菱形の4点のポイントのリストを算出する。
     *
     * @param relationIntersectPoint 関係先と関係のぶつかる交点
     * @param relationSourceIntersectPoint 関係元と関係のぶつかる交点
     * @return コンポジションを表す黒抜きの菱形の4点のリスト
     */
    private List< Point2D > calculateEdgeCompositionPoints( Point2D relationIntersectPoint, Point2D relationSourceIntersectPoint ) {
        double angle = calculateDegreeFromStart( relationSourceIntersectPoint, relationIntersectPoint ); // 関係の始点における角度
        double length = 15.0;
        List< Point2D > compositionPoints = new ArrayList<>();

        compositionPoints.add( new Point2D( relationSourceIntersectPoint.getX(), relationSourceIntersectPoint.getY() ) );
        compositionPoints.add( calculateUmbrellaPoint( compositionPoints.get( 0 ), angle + 20.0, length ) );
        compositionPoints.add( calculateUmbrellaPoint( compositionPoints.get( 1 ), angle - 20.0, length ) );
        compositionPoints.add( calculateUmbrellaPoint( compositionPoints.get( 0 ), angle - 20.0, length ) );
        compositionPoints.add( new Point2D( relationSourceIntersectPoint.getX(), relationSourceIntersectPoint.getY() ) );

        return compositionPoints;
    }

    /**
     * <p> クラス図キャンバスにおいて、任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイントを算出する。 </p>
     *
     * <p>
     *     角度 {@code angle} は、クラス図キャンバスにおいては次のように表す。 <br><br>
     *              270°                     <br>
     *               |                        <br>
     *               |                        <br>
     *     180°-----*------0° ( != 360° )  <br>
     *               |                        <br>
     *               |                        <br>
     *               90°                     <br><br>
     *     なお、理論上は360以上の数値を入れても問題ないが、なるべく [0, 360) であることが望ましい。
     * </p>
     *
     * @param intersectionPoint 任意のポイント
     * @param angle 傾く角度 0以上360未満の実数
     * @param length 離れる距離 0以上の実数
     * @return 任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイント
     */
    private Point2D calculateUmbrellaPoint( Point2D intersectionPoint, double angle, double length ) {
        Point2D point = new Point2D(
                calculateUmbrellaTipX( length, angle, intersectionPoint.getX() ),
                calculateUmbrellaTipY( length, angle, intersectionPoint.getY() )
        );
        return point;
    }

    /**
     * クラス図キャンバスにおいて、任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイントのX軸を算出する。
     *
     * @param length 離れる距離 0以上の実数
     * @param angle 傾く角度 0以上360未満の実数
     * @param actualX 任意の点のX軸
     * @return 任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイントのX軸
     */
    private double calculateUmbrellaTipX( double length, double angle, double actualX ) {
        return length * Math.cos( Math.toRadians( angle ) ) + actualX;
    }

    /**
     * クラス図キャンバスにおいて、任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイントのY軸を算出する。
     *
     * @param length 離れる距離 0以上の実数
     * @param angle 傾く角度 0以上360未満の実数
     * @param actualY 任意の点のY軸
     * @return 任意の点から角度 {@code angle} 傾いて距離 {@code length} 離れているポイントのY軸
     */
    private double calculateUmbrellaTipY( double length, double angle, double actualY ) {
        return length * Math.sin( Math.toRadians( angle ) ) + actualY;
    }

    /**
     * {@link #createOneEdgeQuadrangleWithMargin(double, Point2D, Point2D)} において、関係からの距離を取得する。
     *
     * @param type 関係の種類
     * @return 関係からの距離 基本的には変化しない
     */
    private double getRelationMarginLength( ContentType type ) {
        return 10.0;
    }

    /**
     * 直線における法線の傾きを計算する。
     *
     * @param startPoint 直線における開始ポイント
     * @param endPoint 直線における終了ポイント
     * @return 法線の傾き {@code startPoint} と {@code endPoint} のY軸の値が等しい場合に限り {@code Infinity} を返す。
     */
    public double calculateNormalLineInclination( Point2D startPoint, Point2D endPoint ) {
        double inclination = calculateInclination( startPoint, endPoint );
        double normalLineInclination = 0.0;

        if( ! Double.isInfinite( inclination ) && inclination != 0.0 ) {
            normalLineInclination = 1 / inclination;
        } else if( Double.isInfinite( inclination ) ) {
            normalLineInclination = 0.0;
        } else if( inclination == 0.0 ) {
            normalLineInclination = Double.POSITIVE_INFINITY;
        }

        return normalLineInclination;
    }

    /**
     * <p> 関係における始点から見た関係の角度を算出する。 </p>
     *
     * <p>
     *     角度 {@code angle} は、クラス図キャンバスにおいては次のように表す。 <br><br>
     *              270°                     <br>
     *               |                        <br>
     *               |                        <br>
     *     180°-----e------0° ( != 360° )  <br>
     *               |                        <br>
     *               |                        <br>
     *               90°                     <br><br>
     *     eとは、関係における終点を表す。
     * </p>
     *
     * @param startPoint 関係における始点のポイント
     * @param endPoint 関係における終点のポイント
     * @return 関係における始点から見た関係の角度 0以上360未満の実数
     */
    public double calculateDegreeFromStart( Point2D startPoint, Point2D endPoint ) {
        double inclination = Math.acos( ( startPoint.getX() - endPoint.getX() )
                / Math.sqrt( ( startPoint.getX() - endPoint.getX() ) * ( startPoint.getX() - endPoint.getX() ) + ( startPoint.getY() - endPoint.getY() ) * ( startPoint.getY() - endPoint.getY() ) ) );
        double angle = Math.toDegrees( inclination );

        if( startPoint.getY() < endPoint.getY() ) angle = 180 - angle; // 開始ノードが終了ノードより上に位置している場合
        else if( startPoint.getY() > endPoint.getY() ) angle = 180 + angle;
        else angle = 180 - angle; // 2つのノードが真横に存在している場合 angle == 180 or 360

        return angle;
    }

    /**
     * <p> 関係の終点のノードの縁と関係との交点を算出する。 </p>
     *
     * <p>
     *     具体的には、次のような点pを算出する。<br><br>
     *     ----------------- <br>
     *     |               | <br>
     *     |       e       | <br>
     *     |      /        | <br>
     *     ------p---------- <br>
     *          /            <br>
     *         /             <br>
     *        /              <br>
     *       s               <br><br>
     *     ここで、sとは関係の始点、eとは関係の終点を表す。
     * </p>
     *
     * @param startPoint 関係の始点
     * @param endPoint 関係の終点
     * @param endNodeWidth 関係の終点のノードの幅
     * @param endNodeHeight 関係の終点のノードの高さ
     * @return 関係の終点のノードの縁と関係との交点
     */
    public Point2D calculateIntersectionPointLineAndEndNodeSide( Point2D startPoint, Point2D endPoint, double endNodeWidth, double endNodeHeight ) {
        Point2D point;
        List< Point2D > tops = util.calculateTopListFromNode( endPoint, endNodeWidth, endNodeHeight );
        Point2D upperLeft = tops.get( 3 );
        Point2D upperRight = tops.get( 0 );
        Point2D bottomLeft = tops.get( 2 );
        Point2D bottomRight = tops.get( 1 );
        boolean isHigherThatStartNode = isHigherThanSecondNodeThatFirstNode( startPoint, endPoint );
        boolean isLefterThatStartNode = isLefterThanSecondNodeThatFirstNode( startPoint, endPoint );
        boolean isIntersectedFromUpper = isIntersectedFromUpperOrBottomSideInSecondNode( startPoint, endPoint, endNodeWidth, endNodeHeight );
        boolean isIntersectedFromBottom = isIntersectedFromUpper; // コードの可視化を上げるために作ってるだけだよ

        // 終点に位置するノードを原点とすると、始点ノードは...

        if( isHigherThatStartNode ) {
            if( isLefterThatStartNode ) {
                // 第二象限
                if( isIntersectedFromUpper ) point = util.calculateCrossPoint( startPoint, endPoint, upperLeft, upperRight );
                else point = util.calculateCrossPoint( startPoint, endPoint, upperLeft, bottomLeft );

            } else {
                // 第一象限（Y軸上に位置する場合を含む）
                if( isIntersectedFromUpper ) point = util.calculateCrossPoint( startPoint, endPoint, upperLeft, upperRight );
                else point = util.calculateCrossPoint( startPoint, endPoint, upperRight, bottomRight );
            }

        } else {
            if( isLefterThatStartNode ) {
                // 第三象限（X軸上に位置する場合を含む）
                if( isIntersectedFromBottom ) point = util.calculateCrossPoint( startPoint, endPoint, bottomLeft, bottomRight );
                else point = util.calculateCrossPoint( startPoint, endPoint, upperLeft, bottomLeft );

            } else {
                // 第四象限（X軸上およびY軸上に位置する場合を含む）
                if( isIntersectedFromBottom ) point = util.calculateCrossPoint( startPoint, endPoint, bottomLeft, bottomRight );
                else point = util.calculateCrossPoint( startPoint, endPoint, upperRight, bottomRight );
            }
        }

        return point;
    }

    /**
     * 任意のポイント2点を比較し、最初のポイントの方が高い位置（Y軸上で比較して小さい場合）に存在する場合は真を返す。
     *
     * @param firstNode 任意のポイント
     * @param secondNode 任意のポイント
     * @return 最初のポイントの方が高い位置に存在するか否かの真偽値 同じY軸上にある場合は偽を返す。
     */
    public boolean isHigherThanSecondNodeThatFirstNode( Point2D firstNode, Point2D secondNode ) {
        return firstNode.subtract( secondNode ).getY() < 0;
    }

    /**
     * 任意のポイント2点を比較し、最初のポイントの方が左側（X軸上で比較して小さい場合）に存在する場合は真を返す。
     *
     * @param firstNode 任意のポイント
     * @param secondNode 任意のポイント
     * @return 最初のポイントの方が高い位置に存在するか否かの真偽値 同じX軸上に存在する場合は偽を返す。
     */
    public boolean isLefterThanSecondNodeThatFirstNode( Point2D firstNode, Point2D secondNode ) {
        return firstNode.subtract( secondNode ).getX() < 0;
    }

    /**
     * <p> 関係の終点の上下の側辺と関係の線が交差している場合は真を返す。 </p>
     *
     * <p>
     *     具体的な真偽を下図に示す。
     *     なお、sとは関係の始点、eとは関係の終点、pとは関係の終点のノードの縁と関係との交点を表す。
     * </p>
     *
     * <p>
     *     真の場合 <br><br>
     *
     *     ----------------- <br>
     *     |               | <br>
     *     |       e       | <br>
     *     |      /        | <br>
     *     ------p---------- <br>
     *          /            <br>
     *         /             <br>
     *        /              <br>
     *       s
     * </p>
     *
     * <p>
     *     偽の場合 <br><br>
     *
     *     -----------------           <br>
     *     |               |           <br>
     *     |       e ------p---------s <br>
     *     |               |           <br>
     *     -----------------
     * </p>
     *
     * @param firstNode 関係の始点
     * @param secondNode 関係の終点
     * @param secondNodeWidth 関係の終点のノードの幅
     * @param secondNodeHeight 関係の終点のノードの高さ
     * @return 関係の終点の上下の側辺と関係の線が交差しているか否かの真偽値
     */
    public boolean isIntersectedFromUpperOrBottomSideInSecondNode( Point2D firstNode, Point2D secondNode, double secondNodeWidth, double secondNodeHeight ) {
        boolean isIntersected = false;

        List< Point2D > tops = util.calculateTopListFromNode( secondNode, secondNodeWidth, secondNodeHeight );
        Point2D upperLeft = tops.get( 3 );
        Point2D upperRight = tops.get( 0 );
        Point2D bottomLeft = tops.get( 2 );
        Point2D bottomRight = tops.get( 1 );

        if( util.isIntersected( firstNode, secondNode, upperLeft, upperRight ) || util.isIntersected( firstNode, secondNode, bottomLeft, bottomRight ) ) isIntersected = true;

        return isIntersected;
    }

    /**
     * 2点を結ぶ直線の傾きを計算する。
     *
     * @param startPoint 直線における開始ポイント
     * @param endPoint 直線における終了ポイント
     * @return 法線の傾き {@code startPoint} と {@code endPoint} のX軸の値が等しい場合に限り {@code Infinity} を返す。
     */
    private double calculateInclination( Point2D startPoint, Point2D endPoint ) {
        double inclination;

        double width = startPoint.getX() - endPoint.getX();
        double height = startPoint.getY() - endPoint.getY();

        if( width != 0.0 ) {
            inclination = height / width;
        } else {
            inclination = Double.POSITIVE_INFINITY;
        }

        return inclination;
    }
}
