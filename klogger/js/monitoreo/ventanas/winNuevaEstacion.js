/* 
 * Permite desplegar la ventana para ingresar una nueva estacion
 */

var winNuevaEstacion;
var panelNuevaEstacion;
var formNuevaEstacion;

Ext.onReady(function(){

    var fecha = new Ext.form.DateField({
        fieldLabel  : 'Fecha de Instalaci\xF3n',
        xtype       : 'datefield',
        format      : 'Y-m-d', //YYYY-MMM-DD
        id          : 'fechaInstalacion',
        name        : 'strFecha',
        width       : 140,
        allowBlank  : false,
        emptyText   : 'Fecha...',
        anchor      : '98%'
    });
 
    formNuevaEstacion = new Ext.FormPanel({
        labelAlign  : 'left',
        frame       : true,
        bodyStyle   : 'padding:5px 5px 0',
        labelWidth  : 150,

        items: [{
            layout  : 'form',
            defaults: {
                width: 250
            },
            defaultType: 'textfield',
            items: [{
                fieldLabel  : 'Nombre Estaci\xF3n',
                name        : 'strNombreEst',
                allowBlank  : false
            }, {
                fieldLabel  : 'Direcci\xF3n',
                name        : 'strDireccion',
                allowBlank  : false
            }, {
                fieldLabel  : 'Responsable de la Estaci\xF3n',
                name        : 'strResponsable',
                allowBlank  : false
            }, {
                fieldLabel  : 'Contacto del Responsable',
                name        : 'strContactoResponsable',
                allowBlank  : false
            }, {
                fieldLabel  : 'Id del Modem',
                name        : 'strIdEquipo',
                allowBlank  : false
            },
            fecha,
            new Ext.form.TimeField({
                fieldLabel  : 'Hora de Instalaci\xF3n',
                name        : 'strHora',
                format      : 'H:i',
                minValue    : '00:00',
                maxValue    : '23:59',
                allowBlank  : false
            })            
            ]
        }],

        buttons: [{
            text    : 'Guardar',
            id      : 'btnGuardarEstacion',
            handler: function() {
                
                formNuevaEstacion.getForm().submit({
                    url     : 'php/monitoreo/guardarNuevaEstacion.php',
                    method  : 'POST',
                    waitMsg : 'Guardando Estaci\xF3n...',
                    failure : function (form, action) {
                        Ext.MessageBox.show({
                            title   : 'Error...',
                            msg     : 'No se pudo guardar la estaci\xF3n...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.ERROR
                        });
                    },
                    success: function (form, action) {
                        winNuevaEstacion.hide();
                        Ext.MessageBox.show({
                            title   : 'Exito...',
                            msg     : 'Estaci\xF3n guardada con \xE9xito...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.INFO
                        });
                    }
                });  
                
            }
        }]
    });

    panelNuevaEstacion = new Ext.Panel({
        layout: {
            type  : 'vbox',
            align : 'stretch',
            pack  : 'start'
        },
        border: false,
        items:[formNuevaEstacion]
    });

});

/**
 * Limpia los campos para salir de la ventana
 */
function limpiarVentana(){
    //Limpia las capas antes de hacer una nueva consulta
    limpiarCapas();

    winNuevaEstacion.hide();
    formNuevaEstacion.getForm().reset();
}

/**
 * Muestra la ventana para ingreso de una nueva estación
 * @return NO retorna valor
 */
function ventanaNuevaEstacion(){
    if(!winNuevaEstacion){
        winNuevaEstacion = new Ext.Window({
            layout      : 'fit',
            title       : 'Nueva Estaci\xF3n',
            id          : 'vtnNuevaEstacion',
            resizable   : false,
            width       : 450,
            height      : 265,
            closeAction : 'hide',
            plain       : false,
            items       : [panelNuevaEstacion]
        });
    }
    formNuevaEstacion.getForm().reset();
    winNuevaEstacion.show(this);
}