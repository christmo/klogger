/* 
 * Tab para generar las graficas de manera histórica
 */
var id_est;
var fecha_ini;
var fecha_fin;

function agregarTabHistorico(est,fecIni,horaIni,fecFin,horaFin){
    id_est = est;
    fecha_ini = fecIni+'%20'+horaIni;
    fecha_fin = fecFin+'%20'+horaFin;
    var main = new Ext.Panel({
        id: 'table-panel',
        
        layout: {
            type: 'table',
            columns: 2
        },
        defaults: {
            bodyStyle: 'padding:15px 15px'
        },
        items: [
        crearGraficaHistorico(1), crearGraficaHistorico(2), 
        crearGraficaHistorico(3),crearGraficaHistorico(4), 
        crearGraficaHistorico(5),crearGraficaHistorico(6)
        ]
    });

    var tab = new Ext.Panel({
        title       : "Reporte Hist\xF3rico" + '...',
        closable    : true, //<-- este tab se puede cerrar
        iconCls     : 'app-icon',
        autoScroll  : true,
        id          : 'tabH',
        items       : main
    });

    start.add(tab);
    start.setActiveTab(tab);
    actualizarGraficasHistoricas();
}

function actualizarGraficasHistoricas(){
    var tb = Ext.get('tabH');
    if(tb!= undefined){
        if(tb.hidden!=false){
            cargarStoresGraficasHistorico(1);
            cargarStoresGraficasHistorico(2);
            cargarStoresGraficasHistorico(3);
            cargarStoresGraficasHistorico(4);
            cargarStoresGraficasHistorico(5);
            cargarStoresGraficasHistorico(6);
        }
    }
}

function cargarStoresGraficasHistorico(id_var){
    var store = Ext.StoreMgr.lookup('store'+id_var);
    store.proxy.conn.url = './php/monitoreo/graficas/getMedicionesHistoricas.php?id_var='+id_var+'&id_est='+id_est+'&fecha_ini='+fecha_ini+'&fecha_fin='+fecha_fin;
    store.load();
    console.info('Actualizar');
}

/**
 * Crea todo el componente de la grafica para que sea mostrada 
 */
function crearGraficaHistorico(id_var){
    var store = new Ext.data.JsonStore({
        autoDestroy : true,
        autoLoad    : true,
        storeId     : 'store'+id_var,
        name        : 'str'+id_var,
        url         : './php/monitoreo/graficas/getMedicionesHistoricas.php?id_var='+id_var+'&id_est='+id_est+'&fecha_ini='+fecha_ini+'&fecha_fin='+fecha_fin,
        root        : 'serie',
        fields      : ['time', 'value'],
        failure: function (form, action) {
            Ext.MessageBox.show({
                title   : 'Error...',
                msg     : 'No a ingresado correctamente vuelva a ingresar al sistema...',
                buttons : Ext.MessageBox.OK,
                icon    : Ext.MessageBox.ERROR
            });
        }
    });
		
    var lineChart = new Ext.chart.LineChart({
        store: store,
        xField: 'time',
        yField: 'value'
    });
		
    var panel = new Ext.Panel({
        title : 'Gr\xE1fica '+ id_var,
        width : 500,
        height: 250,
        items:[
        lineChart
        ]
    });
    
    var grafica = new Ext.form.FormPanel({
        defaults: {
            bodyStyle: 'padding:15px 15px'
        },
        items:[
        panel
        ]
    });
    
    return grafica;
}
