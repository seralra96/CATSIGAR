var World = {
    loaded: false,
    drawables: [],

    firetruckRotation: {
        x: 0,
        y: 0,
        z: 0
    },
    firetruckCenter: {
        x: 0,
        y: -0.14,
        z: 0
    },
    firetruckLength: 1.65,
    firetruckHeight: 1.48,

    init: function initFn() {
        World.createOccluder();
        World.createCones();
        World.createMarkers();
        World.createTracker();
        World.createLabel();
        World.createImage();
    },

    createOccluder: function createOccluderFn() {
        var occluderScale = 0.0035 * this.firetruckLength;

        this.firetruckOccluder = new AR.Occluder("assets/firetruck_occluder.wt3", {
            onLoaded: this.loadingStep,
            scale: {
                x: occluderScale,
                y: occluderScale,
                z: occluderScale
            },
            translate: this.firetruckCenter,
            rotate: {
                x: 180
            }
        });
        //World.drawables.push(this.firetruckOccluder);
    },

    createCones: function createConesFn() {
        var coneDistance = this.firetruckLength * 0.7;

        var frontLeftCone = World.getCone(-coneDistance, +coneDistance);
        World.drawables.push(frontLeftCone);

        var backLeftCone = World.getCone(+coneDistance, +coneDistance);
        World.drawables.push(backLeftCone);

        var backRightCone = World.getCone(+coneDistance, -coneDistance);
        World.drawables.push(backRightCone);

        var frontRightCone = World.getCone(-coneDistance, -coneDistance);
        World.drawables.push(frontRightCone);
    },

    getCone: function getConeFn(positionX, positionZ) {
        var coneScale = 0.05 * this.firetruckLength;

        return new AR.Model("assets/traffic_cone.wt3", {
            scale: {
                x: coneScale,
                y: coneScale,
                z: coneScale
            },
            translate: {
                x: positionX,
                y: World.firetruckCenter.y,
                z: positionZ
            },
            rotate: {
                x: -90
            }
        });
    },


    createMarkers: function createMarkersFn() {
        var markerDistance = this.firetruckLength;

        var frontLeftMarker = World.getMarker(0.895, -10.65);
        World.drawables.push(frontLeftMarker);

        //var backRightMarker = World.getMarker(+markerDistance, -markerDistance);
        //World.drawables.push(backRightMarker);

    },

    getMarker: function getMarkerFn(positionX, positionZ) {
        var coneScale = 0.0170 * this.firetruckLength;
        var positionY = -2.0 + World.firetruckCenter.y;
        return new AR.Model("assets/006208032020.wt3", {
            scale: {
                x: coneScale,
                y: coneScale,
                z: coneScale
            },
            translate: {
                x: positionX,
                y: positionY,
                z: positionZ
            },
            rotate: {
                x: -90
            }
        });
    },

    createLabel: function createLabelFn() {
        var texto = new AR.Label("HOLA SOY UNA PIRAMIDE", 10, {
          translate : { x: 0,
                        y: 1.26,
                        z: 0},
          rotate : { z: 0 },
          zOrder: 2,
          onClick : function() {
            label.text += "HOLA SOY UNA PIRAMIDE al darle click"
          },
          verticalAnchor : AR.CONST.VERTICAL_ANCHOR.MIDDLE,
          horizontalAnchor : AR.CONST.HORIZONTAL_ANCHOR.MIDDLE,
          opacity : 1
        });
        console.log("Label creado");
        console.log(World.drawables);
        World.drawables.push(texto);
        console.log("Label llevado a drawables");
        console.log(World.drawables);
    },

    createImage: function createImageFn(){
        var imgOne = new AR.ImageResource("assets/impresora.jpg");
        var imagen = new AR.ImageDrawable(imgOne, 10, {
           translate : { x: -0.845,
                         y: 1.26,
                         z: 0},
          rotate : { z: 0 },
          zOrder: 1,
          onClick : function() {
              // 'this' represents the ImageDrawable
              this.rotate.z += 10;
            }
        });
        World.drawables.push(imagen);
        console.log("Imagen llevada drawables");
        console.log(World.drawables);
    },

    createTracker: function createTrackerFn() {
        this.targetCollectionResource = new AR.TargetCollectionResource("assets/006208032020.wto", {
        });

        this.tracker = new AR.ObjectTracker(this.targetCollectionResource, {
            onError: function(errorMessage) {
                alert(errorMessage);
            }
        });
        
        this.objectTrackable = new AR.ObjectTrackable(this.tracker, "*", {
            drawables: {
                cam: World.drawables
            },
            onObjectRecognized: this.objectRecognized,
            onObjectLost: this.objectLost,
            onError: function(errorMessage) {
                alert(errorMessage);
            }
        });
    },

    objectRecognized: function objectRecognizedFn() {
        World.removeLoadingBar();
        World.setAugmentationsEnabled(true);
    },

    objectLost: function objectLostFn() {
        World.setAugmentationsEnabled(false);
    },

    setAugmentationsEnabled: function setAugmentationsEnabledFn(enabled) {
        for (var i = 0; i < World.drawables.length; i++) {
            World.drawables[i].enabled = enabled;
        }
    },

    removeLoadingBar: function removeLoadingBarFn() {
        if (!World.loaded && World.firetruckOccluder.isLoaded()) {
            var e = document.getElementById('loadingMessage');
            e.parentElement.removeChild(e);
            World.loaded = true;
        }
    },

    loadingStep: function loadingStepFn() {
        if (World.firetruckOccluder.isLoaded()) {
            var cssDivLeft = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
            var cssDivRight = " style='display: table-cell;vertical-align: middle; text-align: left;'";
            document.getElementById('loadingMessage').innerHTML =
                "<div" + cssDivLeft + ">Scan Piramid:</div>" +
                "<div" + cssDivRight + "><img src='assets/firetruck_image.png'></img></div>";
        }
    }
};

World.init();
