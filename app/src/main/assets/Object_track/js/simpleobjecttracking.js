var World = {
    loaded: false,
    drawables: [],
    construcciones:[
        {
            id: "otros",
            x: -0.05,
            y: 0,
            z: -0.14,
            sc: 0.00825,
            url: "assets/piramide.wt3",
            wtourl: "assets/piramide.wto",
            drawable: null
        },
        {
            id: "006208032020",
            x: -0.142,
            y: -0.092,
            z: 0.245,
            sc: 0.00385,
            url: "assets/006208032020.wt3",
            wtourl: "assets/006208032020.wto",
            drawable: null
        },
        {
            id: "006208032021",
            x: -0.449,
            y: 0.187,
            z: -0.132,
            sc: 0.00785,
            url: "assets/006208032021.wt3",
            wtourl: "assets/006208032021.wto",
            drawable: null
        }

    ],
    edRotation: {
        x: 0,
        y: 0,
        z: 0
    },
    edCenter: {
        x: 0,
        y: -0.14,
        z: 0
    },
    edength: 1.65,
    edHeight: 1.48,

    init: function initFn() {
        World.createCones();
        World.createMarkers();
        //World.createTracker();
    },


    createCones: function createConesFn() {
        var coneDistance = this.edLength * 0.7;

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
        var coneScale = 0.05 * this.edLength;

        return new AR.Model("assets/traffic_cone.wt3", {
            scale: {
                x: coneScale,
                y: coneScale,
                z: coneScale
            },
            translate: {
                x: positionX,
                y: World.edCenter.y,
                z: positionZ
            },
            rotate: {
                x: -90
            }
        });
    },


    createMarkers: function createMarkersFn() {

        function search(idLote, array){
            for (var i=0; i < array.length; i++){
                if (array[i].id === idLote){
                    return true;
                }
                }
            return false;
            }

        console.log("prueba de mensaje recibido codigo predial "+ GET.codpre);
        console.log(search(GET.codpre, World.construcciones));
        if (search(GET.codpre, World.construcciones)){
            console.log("La construccion "+GET.codpre+ " existe y se dibujará");
              for (var i=0; i < World.construcciones.length; i++){
                            if (World.construcciones[i].id === GET.codpre){
                                construccion = World.construcciones[i];
                                construccion.drawable = World.getMarker(construccion.x, construccion.y, construccion.z, construccion.url, construccion.sc);
                                World.drawables.push(construccion.drawable);
                                World.createTracker(construccion.wtourl);
                                break;
                            }
            }
        }else{
            alert("La construccion "+GET.codpre+ " no existe, pero puedes ver información de la pirámide de muestra");
            console.log("La construccion "+GET.codpre+ " no existe y se mostrará la pirámide");
            construccion = World.construcciones[0];
            construccion.drawable = World.getMarker(construccion.x, construccion.y, construccion.z, construccion.url, construccion.sc);
            World.drawables.push(construccion.drawable);
            World.createTracker(construccion.wtourl);
        }

/*
        var markerDistance = this.edLength;
        console.log("prueba de mensaje recibido codigo predial "+ GET.codpre);
        var ed006208032020 = World.getMarker(0.895, -2.0, -10.65, "assets/006208032020.wt3", 0.0170);
        World.drawables.push(ed006208032020);
*/
        //var backRightMarker = World.getMarker(+markerDistance, -markerDistance);
        //World.drawables.push(backRightMarker);

    },

    getMarker: function getMarkerFn(positionX, positionY, positionZ, model, coneScale) {
        //var coneScale = 0.0170 * this.firetruckLength;
        //var positionY = -2.0 + World.firetruckCenter.y;
        return new AR.Model(model, {
            onLoaded: this.loadingStep,
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

    createTracker: function createTrackerFn(url) {
        this.targetCollectionResource = new AR.TargetCollectionResource(url, {
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
        if (!World.loaded) {
            var e = document.getElementById('loadingMessage');
            e.parentElement.removeChild(e);
            World.loaded = true;
        }
    },

    loadingStep: function loadingStepFn() {
            var cssDivCenter = " style='display: table-cell;vertical-align: middle; text-align: center;'";
            document.getElementById('loadingMessage').innerHTML =
                "<div" + cssDivCenter + ">Escanea la Construcción:</div>" ;
    }
};

World.init();
