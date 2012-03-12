/* 
 * Permite desplegar la ventana para ingresar las configuraciones
 */

var winConfiguraciones;
var panelConfiguraciones;
var formConfiguracion;
var spiner;
var id_est;


Ext.onReady(function(){
    
    spiner = new Ext.ux.form.Spinner({
        fieldLabel  : 'Tiempo de Reporte (min)',
        width       : 80,
        name        : 'tiempo',
        value       : '10',
        strategy:{
            xtype:'number',
            minValue:10,
            maxValue:1440
        },
        allowBlank  : false,
        emptyText   : 'minutos'
    }); 
 
    formConfiguracion = new Ext.FormPanel({
        labelAlign  : 'left',
        frame       : true,
        bodyStyle   : 'padding:5px 5px 0',
        labelWidth  : 150,
        width       : 500,

        items: [{
            layout  : 'form',
            items   : [
            spiner
            ]
        }],

        buttons: [{
            text    : 'Guardar',
            id      : 'btnGuardarRuta',
            handler: function() {
                var tiempo = spiner.getValue();
                if(tiempo==""){
                    Ext.MessageBox.show({
                        title   : 'Error...',
                        msg     : 'Debe escoger un tiempo de reporte...',
                        buttons : Ext.MessageBox.OK,
                        icon    : Ext.MessageBox.ERROR
                    });
                }else{
                    if(tiempo>=10 && tiempo<=1440){
                        formConfiguracion.getForm().submit({
                            url     : 'php/monitoreo/guardarConfiguraciones.php',
                            method  : 'POST',
                            waitMsg : 'Guardando Configuraci\xF3n...',
                            params  :{
                                id_est: id_est,
                                tiempo: tiempo
                            },
                            failure : function (form, action) {
                                Ext.MessageBox.show({
                                    title   : 'Error...',
                                    msg     : 'No se pudo guardar la configuraci\xF3n...',
                                    buttons : Ext.MessageBox.OK,
                                    icon    : Ext.MessageBox.ERROR
                                });
                            },
                            success: function (form, action) {
                                winConfiguraciones.hide();
                                Ext.MessageBox.show({
                                    title   : 'Exito...',
                                    msg     : 'Configuraci\xF3n realizada con \xE9xito...',
                                    buttons : Ext.MessageBox.OK,
                                    icon    : Ext.MessageBox.INFO
                                });
                            }
                        });  
                    }else{
                        Ext.MessageBox.show({
                            title   : 'Error...',
                            msg     : 'Tiempo fuera de rango (10-1440)...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.ERROR
                        });
                    }
                }
            }
        }]
       
    });

    panelConfiguraciones = new Ext.Panel({
        layout: {
            type  : 'vbox',
            align : 'stretch',
            pack  : 'start'
        },
        border: false,
        items:[formConfiguracion]
    });

});

/**
 * Limpia los campos para salir de la ventana
 */
function limpiarVentana(){
    //Limpia las capas antes de hacer una nueva consulta
    limpiarCapas();

    winConfiguraciones.hide();
    formConfiguracion.getForm().reset();
}

/**
 * Muestra la ventana para CONFIGURAR EL TIEMPO DE REPORTE
 * @return NO retorna valor
 */
function ventanaConfiguracion(id_estacion){
    id_est = id_estacion;
    if(!winConfiguraciones){
        winConfiguraciones = new Ext.Window({
            layout      : 'fit',
            title       : 'Configuraci\xF3n',
            id          : 'vtnConfiguracion',
            resizable   : false,
            width       : 280,
            height      : 110,
            closeAction : 'hide',
            plain       : false,
            items       : [panelConfiguraciones]
        });
    }
    formConfiguracion.getForm().reset();
    winConfiguraciones.show(this);
}