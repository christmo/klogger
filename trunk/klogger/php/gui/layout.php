<?phpinclude "php/login/isLogin.php";echo "<script type='text/javascript'>    var tmpAlerts;    //Tiempo para consulta de alertas    var placaVh;    var sb;    var seguirVehiculo = false;    Ext.onReady(function(){        var tb = new Ext.Toolbar();        var idEstacion;                var mnuContext = new Ext.menu.Menu({            items: [{                    id: 'opt1',                    text: 'Gráficas'                },{                    id: 'opt2',                    text: 'Configurar'                }                ],            listeners: {                itemclick: function(item) {                    if (item.id == 'opt1'){                        agregarTab(\"Tiempo Real\",idEstacion);                    }                    if (item.id == 'opt2'){                        ventanaConfiguracion(idEstacion);                    }                }            }        });        var boxCheck = {            xtype: 'checkbox',            boxLabel: 'Seguir Vehículo',            handler: function(d){                if (d.getValue()) {                    seguirVehiculo = true;                }else{                    seguirVehiculo = false;                }            }        };        tb.add('-',{            xtype: 'tbbutton',            cls: 'x-btn-text-icon',            icon: 'img/historico.png',            text: '".utf8_encode("Administraci\xF3n")."',            menu: [{                    text: '".utf8_encode("Nueva Estaci\xF3n")."',                    icon: 'img/lapiz.png',                    handler: function(){                        ventanaNuevaEstacion();                    }                },                {                    text: '".utf8_encode("Borrar Estaci\xF3n")."',                    icon: 'img/borrar.png',                    handler: function(){                        ventanaBorrarEstacion();                    }                },'-',//                {//                    text: 'Simbología',//                    icon: 'img/paleta.png',//                    handler: function(){//                        ventanaSimbolos();//                    }//                }            ]        },     '-', {            text: '".  utf8_encode("Recuperar Datos Perdidos")."',            icon : 'img/config.png',            handler: function(){                ventanaRecuperarDatos();            }        },        '-',{            xtype: 'tbbutton',            cls: 'x-btn-text-icon',            icon: 'img/reportMain.png',            text: 'Reportes',            menu: [{                    text: '".utf8_encode("Hist\xF3rico")."',                    icon: 'img/general.png',                    handler: function(){                        ventanaReporteHistorico();                    }                }            ]        },        '-',";echo "{            xtype: 'tbbutton',            icon: 'img/panel.png',            text: 'Aplicacion',            menu: [{                    text: 'Acerca de...',                    icon : 'img/info.png',                    handler: function(){                        credits();                    }                },                {                    text: 'Ayuda',                    icon : 'img/help.png',                    handler: function(){                        //Ayuda sobre la App                    }                }]        }, {            text: 'Salir',            icon : 'img/salir.png',            handler: function(){                window.location = 'php/login/logout.php';            }        });        tb.doLayout();        var BarTool = {            id: 'content-panel-bar',            region: 'center',            layout: 'fit',            margins: '0 0 0 0',            tbar: tb,            border: false        };        new Ext.Viewport({            layout: 'border',            items: [                {                    region: 'north',                    height: 100,                    items:[{                            height: 70,                            html: '<div id =";echo '"header"';echo "><h1><CENTER> <b>SISTEMA DE MONITOREO REMOTO <br /> Loja-Ecuador</b></CENTER>Bienvenido:: " . $_SESSION["USER"] . " al sistema</h1></div>'            },            BarTool            ]        },        {            region: 'west',            id: 'west-panel',            split: true,            width: 200,            minSize: 175,            maxSize: 400,            title: 'Estaciones',            collapsible: true,            margins: '0 0 0 5',            xtype: 'treepanel',            autoScroll: true,            icon : 'img/help.png',            dataUrl: 'php/monitoreo/getEstaciones.php',            root: {                nodeType: 'async',                text: 'Ext JS',                draggable: false,                id: 'src'            },            rootVisible: false,            listeners: {                click: function(n) { ";echo "                      var aux = n.attributes.id;                       if (!isNaN(aux)) {                           buscarVehiculo('T'+aux);                       }else{                           return;                       }                },                              contextmenu: function(e) {                              idEstacion = e.attributes.id;                placaVh = e.attributes.text;                var posXY = new Array(2) ;                posXY[0] = window.event.clientX ;                posXY[1] = window.event.clientY ;                mnuContext.showAt(posXY);                              }                         }        },panelCentral],        renderTo: Ext.getBody()    });});   </script>";?>