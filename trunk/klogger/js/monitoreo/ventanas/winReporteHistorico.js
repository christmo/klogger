/* 
 * Permite desplegar la ventana para hacer la busqueda de un reporte
 */

var winResporteHistorico;
var panelReporteHistorico;
var formReporteHistorico;
var storeEstacionesH;

Ext.onReady(function(){

    Ext.apply(Ext.form.VTypes, {
        daterange : function(val, field) {            
            var date = field.parseDate(val);            
            if(!date){
                return;
            }
            if (field.startDateField && (!this.dateRangeMax ||
                (date.getTime() != this.dateRangeMax.getTime()))) {
                var start = Ext.getCmp(field.startDateField);
                start.setMaxValue(date);
                start.validate();
                this.dateRangeMax = date;
            }
            else if (field.endDateField && (!this.dateRangeMin ||
                (date.getTime() != this.dateRangeMin.getTime()))) {
                var end = Ext.getCmp(field.endDateField);
                end.setMinValue(date);
                end.validate();
                this.dateRangeMin = date;
            }
            return true;
        }
    });

    var fechaIniRepHistorico = new Ext.form.DateField({
        fieldLabel: 'Desde el',
        xtype:'datefield',
        format: 'Y-m-d', //YYYY-MMM-DD
        id: 'fechaIniHistorico',
        name: 'strFechaIniRepHistorico',
        allowBlank:false,
        vtype: 'daterange',
        endDateField: 'fechaFinHistorico',
        emptyText:'Fecha Inicial...',
        anchor:'98%'
    });

    var fechaFinRepHistorico = new Ext.form.DateField({
        fieldLabel: 'Hasta el',
        xtype:'datefield',
        format: 'Y-m-d', //YYYY-MMM-DD
        id: 'fechaFinHistorico',
        name: 'strFechaFinRepHistorico',
        allowBlank:false,
        vtype: 'daterange',
        startDateField: 'fechaIniHistorico',
        emptyText:'Fecha Final...',
        anchor:'98%'

    });
 
    storeEstacionesH = new Ext.data.JsonStore({
        url:'php/monitoreo/combos/cbxEstaciones.php',
        root: 'estaciones',
        fields: [{
            name:'id'
        },{
            name:'name'
        }]
    });
    
    
    var cbxEstacionesH = new Ext.form.ComboBox({
        fieldLabel  : 'Estaciones',
        id          : 'cbxEstacionesH',
        name        : 'cbxEstacionesH',
        store       : storeEstacionesH,
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

    formReporteHistorico = new Ext.FormPanel({
        labelAlign  : 'left',
        frame       : true,
        bodyStyle   : 'padding:5px 5px 0',
        labelWidth  : 100,

        items: [{
            layout      : 'form',
            defaultType : 'textfield',
            items: [
            cbxEstacionesH,
            fechaIniRepHistorico,
            new Ext.form.TimeField({
                fieldLabel  : 'Desde la hora',
                name        : 'strHoraIni',
                format      : 'H:i',
                minValue    : '00:00',
                maxValue    : '23:59',
                anchor      : '98%',
                editable    : false,
                value       : new Date(),
                allowBlank  : false
            }),
            fechaFinRepHistorico,
            new Ext.form.TimeField({
                fieldLabel  : 'Hasta la hora',
                name        : 'strHoraFin',
                format      : 'H:i',
                minValue    : '00:00',
                maxValue    : '23:59',
                anchor      : '98%',
                editable    : false,
                value       : new Date(),
                allowBlank  : false
            })
            ]
        }],

        buttons: [{
            text    : 'Buscar',
            id      : 'btnBuscarHistorico',
            handler: function() {
                agregarTabHistorico(
                    formReporteHistorico.getForm().findField('cbxEstacionesH').getValue(),
                    Ext.getCmp('fechaIniHistorico').value,
                    formReporteHistorico.getForm().findField('strHoraIni').getValue(),
                    Ext.getCmp('fechaFinHistorico').value,
                    formReporteHistorico.getForm().findField('strHoraFin').getValue()
                    );
                winResporteHistorico.hide();
            }
        }]
    });

    panelReporteHistorico = new Ext.Panel({
        layout: {
            type  : 'vbox',
            align : 'stretch',
            pack  : 'start'
        },
        border: false,
        items:[formReporteHistorico]
    });

});

/**
 * Limpia los campos para salir de la ventana
 */
function limpiarVentana(){
    //Limpia las capas antes de hacer una nueva consulta
    limpiarCapas();

    winResporteHistorico.hide();
    formReporteHistorico.getForm().reset();
}

/**
 * Muestra la ventana para buscar un historico de datos
 * @return NO retorna valor
 */
function ventanaReporteHistorico(){
    if(!winResporteHistorico){
        winResporteHistorico = new Ext.Window({
            layout      : 'fit',
            title       : 'Resporte Historico',
            id          : 'vtnReporteHistorico',
            resizable   : false,
            width       : 300,
            height      : 213,
            closeAction : 'hide',
            plain       : false,
            items       : [panelReporteHistorico]
        });
    }
    formReporteHistorico.getForm().reset();
    winResporteHistorico.show(this);
    storeEstacionesH.load();
}