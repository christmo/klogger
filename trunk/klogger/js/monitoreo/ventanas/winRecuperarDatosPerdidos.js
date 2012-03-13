/* 
 * Permite desplegar la ventana para enviar una petición de recuperación al
 * datalogger, esta ventana encola dentro de la base de datos el siguiente comando
 * AT$SMGSND=0,"R120307//" -> donde 120307 es la fecha 2012-03-07
 */

var winRecuperarDatosPerdidos;
var panelRecuperarDatosPerdidos;
var formRecuperarDatosPerdidos;

Ext.onReady(function(){

    var storeEstaciones = new Ext.data.JsonStore({
        url:'php/monitoreo/combos/cbxEstaciones.php',
        root: 'estaciones',
        fields: [{
            name:'id'
        },{
            name:'name'
        }]
    });
    storeEstaciones.load();
    
    var cbxEstacionesRD = new Ext.form.ComboBox({
        fieldLabel  : 'Estaciones',
        id          : 'cbxRecuperarDatos',
        name        : 'cbxRecuperarDatos',
        store       : storeEstaciones,
        hiddenName  : 'idEstacion',
        valueField  : 'id',
        displayField: 'name',
        typeAhead   : true,
        disabled    : false,
        mode        : 'local',
        triggerAction:'all',
        emptyText   : 'Seleccionar Estaci\xF3n...',
        allowBlank  : false,
        resizable   : true,
        minListWidth: 300,
        selectOnFocus:true,
        anchor      : '98%'
    });
    
    var fecha = new Ext.form.DateField({
        fieldLabel  : 'Recuperar datos de',
        xtype       : 'datefield',
        format      : 'ymd',//Fecha en formato 120307
        id          : 'fechaInstalacion',
        name        : 'fechaRecuperar',
        width       : 140,
        allowBlank  : false,
        emptyText   : 'Fecha...',
        anchor      : '98%'
    });
    
    formRecuperarDatosPerdidos = new Ext.FormPanel({
        labelAlign  : 'left',
        frame       : true,
        bodyStyle   : 'padding:5px 5px 0',
        labelWidth  : 80,

        items: [{
            layout  : 'form',
            defaultType: 'textfield',
            items: [
            cbxEstacionesRD,
            fecha
            ]
        }],

        buttons: [{
            text    : 'Recuperar Datos',
            id      : 'btnRecuperarDatos',
            handler: function() {
                
                formRecuperarDatosPerdidos.getForm().submit({
                    url     : 'php/monitoreo/recuperarDatosPerdidos.php',
                    method  : 'POST',
                    waitMsg : 'Recuperando Datos...',
                    params  :{
                        id_est : Ext.getCmp("cbxRecuperarDatos").getValue()
                    },
                    failure : function (form, action) {
                        Ext.MessageBox.show({
                            title   : 'Error...',
                            msg     : 'No se pudo recuperar datos...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.ERROR
                        });
                    },
                    success: function (form, action) {
                        winRecuperarDatosPerdidos.hide();
                        Ext.MessageBox.show({
                            title   : 'Exito...',
                            msg     : 'Enviado el comando de recuperaci\xF3n...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.INFO
                        });
                    }
                });  
                
            }
        }]
    });

    panelRecuperarDatosPerdidos = new Ext.Panel({
        layout: {
            type  : 'vbox',
            align : 'stretch',
            pack  : 'start'
        },
        border: false,
        items:[formRecuperarDatosPerdidos]
    });

});

/**
 * Limpia los campos para salir de la ventana
 */
function limpiarVentana(){
    //Limpia las capas antes de hacer una nueva consulta
    limpiarCapas();

    winRecuperarDatosPerdidos.hide();
    formRecuperarDatosPerdidos.getForm().reset();
}

/**
 * Muestra la ventana para recuperar datos perdidos, a través de esta se encola
 * los comando para que sean enviados al equipo
 * @return NO retorna valor
 */
function ventanaRecuperarDatos(){
    if(!winRecuperarDatosPerdidos){
        winRecuperarDatosPerdidos = new Ext.Window({
            layout      : 'fit',
            title       : 'Recuperar Datos Perdidos',
            id          : 'vtnRecuperarDatos',
            resizable   : false,
            width       : 300,
            height      : 145,
            closeAction : 'hide',
            plain       : false,
            items       : [panelRecuperarDatosPerdidos]
        });
    }
    formRecuperarDatosPerdidos.getForm().reset();
    winRecuperarDatosPerdidos.show(this);
}