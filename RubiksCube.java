
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Simple implementation of the Rubik's cube using JavaFX 3D
 * http://stackoverflow.com/questions/34001900/how-to-render-3d-graphics-properly
 * @author JosePereda
 */
public class RubiksCube extends Application {
    
    public static final int RED     = 0;
    public static final int GREEN   = 1;
    public static final int BLUE    = 2;
    public static final int YELLOW  = 3;
    public static final int ORANGE  = 4;
    public static final int WHITE   = 5;
    public static final int GRAY    = 6;

    public static int katLewa = 0;//obsluguje lewa scianke
    public static int Li = 0;//obsluguje lewa scianke
    public static int katPrawa = 0;//obsluguje prawa scianke
    public static int Pi = 0;//obsluguje prawa scianke
    public static int katGora = 0;//obsluguje gorna scianke
    public static int katDol = 0;//obsluguje gorna scianke

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    
    @Override
    public void start(Stage primaryStage) {
        Group sceneRoot = new Group();
        Scene scene = new Scene(sceneRoot, 600, 600, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-30);
        scene.setCamera(camera);

        PhongMaterial mat = new PhongMaterial();
        // image can be found here http://i.stack.imgur.com/uN4dv.png
        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("newpalette.png")));

        Group meshGroup = new Group();



        AtomicInteger cont = new AtomicInteger();
        List<MeshView> kostki = new ArrayList<>();


        patternFaceF.forEach(p -> {
            ObjModelImporter importer = new ObjModelImporter();
            importer.read(getClass().getResource("color-cube.obj"));
            MeshView meshP = importer.getImport()[0];

            meshP.getTransforms().clear();
            Point3D pt = pointsFaceF.get(cont.getAndIncrement());
            meshP.getTransforms().addAll(new Translate(pt.getX(), pt.getY(), pt.getZ()));
            meshGroup.getChildren().add(meshP);
            kostki.add(meshP);


        });


        
        Rotate rotateX = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(20, 0, 0, 0, Rotate.Y_AXIS);
        meshGroup.getTransforms().addAll(rotateX, rotateY);

        PointLight pointlight = new PointLight();
        pointlight.setColor(Color.web("#e5e5e5"));
        pointlight.setTranslateZ(-470);
        pointlight.setTranslateY(-290);
        pointlight.setTranslateX(-200);



        sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE),pointlight);


        scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle()-(mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle()+(mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });

        KeyCombination ctrl1 = new KeyCodeCombination(KeyCode.NUMPAD1, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl2 = new KeyCodeCombination(KeyCode.NUMPAD2, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl3 = new KeyCodeCombination(KeyCode.NUMPAD3, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl4 = new KeyCodeCombination(KeyCode.NUMPAD4, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl5 = new KeyCodeCombination(KeyCode.NUMPAD5, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl6 = new KeyCodeCombination(KeyCode.NUMPAD6, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl7 = new KeyCodeCombination(KeyCode.NUMPAD7, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrl8 = new KeyCodeCombination(KeyCode.NUMPAD8, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination no1 = new KeyCodeCombination(KeyCode.NUMPAD1);
        KeyCombination no2 = new KeyCodeCombination(KeyCode.NUMPAD2);
        KeyCombination no3 = new KeyCodeCombination(KeyCode.NUMPAD3);
        KeyCombination no4 = new KeyCodeCombination(KeyCode.NUMPAD4);
        KeyCombination no5 = new KeyCodeCombination(KeyCode.NUMPAD5);
        KeyCombination no6 = new KeyCodeCombination(KeyCode.NUMPAD6);
        KeyCombination no7 = new KeyCodeCombination(KeyCode.NUMPAD7);
        KeyCombination no8 = new KeyCodeCombination(KeyCode.NUMPAD8);

    scene.setOnKeyPressed(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent keyEvent) {
            Rotate r;
            Transform t = new Rotate();

            if (no1.match(keyEvent)) {

              Group mesh = new Group();
              mesh.getChildren().addAll(kostki.get(0), kostki.get(2), kostki.get(4), kostki.get(6));
              meshGroup.getChildren().add(mesh);

              katLewa -= 90;
              r = new Rotate(katLewa, Rotate.X_AXIS);
              t = t.createConcatenation(r);
              mesh.translateXProperty().set(0);
              mesh.translateYProperty().set(0);
              mesh.translateZProperty().set(0);
              mesh.getTransforms().clear();
              mesh.getTransforms().addAll(t);

            } else if (no2.match(keyEvent)) {
              Group mesh = new Group();
              mesh.getChildren().addAll(kostki.get(0), kostki.get(2), kostki.get(4), kostki.get(6));
              meshGroup.getChildren().add(mesh);

              katLewa += 90;
              r = new Rotate(katLewa, Rotate.X_AXIS);
              t = t.createConcatenation(r);
              mesh.translateXProperty().set(0);
              mesh.translateYProperty().set(0);
              mesh.translateZProperty().set(0);
              mesh.getTransforms().clear();
              mesh.getTransforms().addAll(t);

            } else if (no3.match(keyEvent)) {
              Group mesh = new Group();
              mesh.getChildren().addAll(kostki.get(1), kostki.get(3), kostki.get(5), kostki.get(7));
              meshGroup.getChildren().add(mesh);

              katPrawa -= 90;
              r = new Rotate(katPrawa, Rotate.X_AXIS);
              t = t.createConcatenation(r);
              mesh.translateXProperty().set(0);
              mesh.translateYProperty().set(0);
              mesh.translateZProperty().set(0);
              mesh.getTransforms().clear();
              mesh.getTransforms().addAll(t);
            } else if (no4.match(keyEvent)) {
              Group mesh = new Group();
              mesh.getChildren().addAll(kostki.get(1), kostki.get(3), kostki.get(5), kostki.get(7));
              meshGroup.getChildren().add(mesh);

              katPrawa += 90;
              r = new Rotate(katPrawa, Rotate.X_AXIS);
              t = t.createConcatenation(r);
              mesh.translateXProperty().set(0);
              mesh.translateYProperty().set(0);
              mesh.translateZProperty().set(0);
              mesh.getTransforms().clear();
              mesh.getTransforms().addAll(t);
            } else if (no5.match(keyEvent)) {
              Group mesh = new Group();
              mesh.getChildren().addAll(kostki.get(0), kostki.get(1), kostki.get(4), kostki.get(5));
              meshGroup.getChildren().add(mesh);

              katGora += 90;
              r = new Rotate(katGora, Rotate.Y_AXIS);
              t = t.createConcatenation(r);
              mesh.translateXProperty().set(0);
              mesh.translateYProperty().set(0);
              mesh.translateZProperty().set(0);
              mesh.getTransforms().clear();
              mesh.getTransforms().addAll(t);
            } else if (no6.match(keyEvent)) {
              System.out.println("6");
            } else if (no7.match(keyEvent)) {
              System.out.println("7");
            } else if (no8.match(keyEvent)) {
              System.out.println("8");
            } else if (ctrl1.match(keyEvent)) {
              System.out.println("Ctrl+1");
            } else if (ctrl2.match(keyEvent)) {
              System.out.println("Ctrl+2");
            } else if (ctrl3.match(keyEvent)) {
              System.out.println("Ctrl+3");
            } else if (ctrl4.match(keyEvent)) {
              System.out.println("Ctrl+4");
            } else if (ctrl5.match(keyEvent)) {
              System.out.println("Ctrl+5");
            } else if (ctrl6.match(keyEvent)) {
              System.out.println("Ctrl+6");
            } else if (ctrl7.match(keyEvent)) {
              System.out.println("Ctrl+7");
            } else if (ctrl8.match(keyEvent)) {
              System.out.println("Ctrl+8");
            }
          }
        });

        primaryStage.setTitle("Simple Rubik's Cube - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    // F   R   U   B   L   D
    private static  double dist = 2.17 / 2;
    private static final int[] CD   = new int[]{BLUE, GRAY, GRAY, GRAY, ORANGE, WHITE};
    private static final int[] CRD  = new int[]{BLUE, RED, GRAY, GRAY, GRAY, WHITE};
    private static final int[] C    = new int[]{BLUE, GRAY, YELLOW, GRAY, ORANGE, GRAY};
    private static final int[] CR   = new int[]{BLUE, RED, YELLOW, GRAY, GRAY, GRAY};

    
    private static  Point3D pCD    = new Point3D(   -dist,  dist, -dist);
    private static  Point3D pCRD   = new Point3D( dist,  dist, -dist);
    private static  Point3D pC     = new Point3D(   -dist,    -dist, -dist);
    private static  Point3D pCR    = new Point3D( dist,    -dist, -dist);


    private static final int[] BD   = new int[]{GRAY, GRAY, GRAY, GREEN, ORANGE, WHITE};
    private static final int[] BRD  = new int[]{GRAY, RED, GRAY, GREEN, GRAY, WHITE};
    private static final int[] B    = new int[]{GRAY, GRAY, YELLOW, GREEN, ORANGE, GRAY};
    private static final int[] BR   = new int[]{GRAY, RED, YELLOW, GREEN, GRAY, GRAY};

    
    private static  Point3D pBD    = new Point3D(   -dist,  dist, dist);
    private static  Point3D pBRD   = new Point3D( dist,  dist, dist);
    private static  Point3D pB     = new Point3D(   -dist,    -dist, dist);
    private static  Point3D pBR    = new Point3D( dist,    -dist, dist);


    private Point3D points[][][] = {
            {
                    {pC,pCR},
                    {pCD,pCRD}
            },
            {
                    {pB,pBR},
                    {pBD,pBRD}
            }
    };
    private int[] colors[][][] = {
            {
                    {C,CR},
                    {CD,CRD}
            },
            {
                    {B,BR},
                    {BD,BRD}
            }
    };


    private final List<int[]> patternFaceF = Arrays.asList(

            colors[0][0][0], colors[0][0][1], colors[0][1][0], colors[0][1][1],
            colors[1][0][0], colors[1][0][1], colors[1][1][0], colors[1][1][1]);
    
    private final List<Point3D> pointsFaceF = Arrays.asList(
            pCD, pCRD,  pC, pCR,
            pBD, pBRD, pB, pBR);

//
//            points[0][0][0], points[0][0][1], points[0][1][0], points[0][1][1],
//            points[1][0][0], points[1][0][1], points[1][1][0], points[1][1][1]);

    public static void main(String[] args) {
        launch(args);
    }
    
}