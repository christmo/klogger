/* 
 * Permite desplegar la ventana para el borrado de estaciones
 */

var winEliminarEstacion;
var panelEliminarEstacion;
var formEliminarEstacion;
var storeEstaciones;

Ext.onReady(function(){

    storeEstaciones = new Ext.data.JsonStore({
        url:'php/monitoreo/combos/cbxEstaciones.php',
        root: 'estaciones',
        fields: [{
            name:'id'
        },{
            name:'name'
        }]
    });
    
    var cbxBorrarEstaciones = new Ext.form.ComboBox({
        fieldLabel  : 'Estaciones',
        id          : 'cbxEliminarEstaciones',
        name        : 'cbxEliminarEstaciones',
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
    
    formEliminarEstacion = new Ext.FormPanel({
        labelAlign  : 'left',
        frame       : true,
        bodyStyle   : 'padding:5px 5px 0',
        labelWidth  : 80,

        items: [{
            layout  : 'form',
            defaultType: 'textfield',
            items: [
            cbxBorrarEstaciones
            ]
        }],

        buttons: [{
            text    : 'Eliminar',
            id      : 'btnEliminarEstacion',
            handler: function() {
                
                formEliminarEstacion.getForm().submit({
                    url     : 'php/monitoreo/eliminarEstacion.php',
                    method  : 'POST',
                    waitMsg : 'Eliminando Estaci\xF3n...',
                    params  :{
                        id_est : Ext.getCmp("cbxEliminarEstaciones").getValue()
                    },
                    failure : function (form, action) {
                        Ext.MessageBox.show({
                            title   : 'Error...',
                            msg     : 'No se pudo eliminar la estaci\xF3n...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.ERROR
                        });
                    },
                    success: function (form, action) {
                        winEliminarEstacion.hide();
                        Ext.MessageBox.show({
                            title   : 'Exito...',
                            msg     : 'Estaci\xF3n eliminada con \xE9xito...',
                            buttons : Ext.MessageBox.OK,
                            icon    : Ext.MessageBox.INFO
                        });
//                        Ext.getCmp('west-panel').getLoader().dataUrl = 'php/monitoreo/getEstaciones.php';
                        Ext.getCmp('west-panel').getRootNode().reload();
                    }
                });  
                
            }
        }]
    });

    panelEliminarEstacion = new Ext.Panel({
        layout: {
            type  : 'vbox',
            align : 'stretch',
            pack  : 'start'
        },
        border: false,
        items:[formEliminarEstacion]
    });

});

/**
 * Limpia los campos para salir de la ventana
 */
function limpiarVentana(){
    //Limpia las capas antes de hacer una nueva consulta
    limpiarCapas();

    winEliminarEstacion.hide();
    formEliminarEstacion.getForm().reset();
}

/**
 * Muestra la ventana para borrar una estacion
 * @return NO retorna valor
 */
function ventanaBorrarEstacion(){
    if(!winEliminarEstacion){
        winEliminarEstacion = new Ext.Window({
            layout      : 'fit',
            title       : 'Eliminar Estaci\xF3n',
            id          : 'vtnEliminarEstacion',
            resizable   : false,
            width       : 300,
            height      : 108,
            closeAction : 'hide',
            plain       : false,
            items       : [panelEliminarEstacion]
        });
    }
    formEliminarEstacion.getForm().reset();
    winEliminarEstacion.show(this);
    
    storeEstaciones.load();
}