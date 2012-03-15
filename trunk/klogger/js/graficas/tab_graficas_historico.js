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
    cargarTabTabla();
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

/**
 * Hace la creacion del componente tabla que se cargara dentro del tab
 */
function cargarTabTabla(){
    var store = new Ext.data.JsonStore({
        autoDestroy : true,
        autoLoad    : true,
        storeId     : 'storeTabla',
        name        : 'strTabla',
        url         : './php/monitoreo/reportes/getDatosHistoricos.php?id_est='+id_est+'&fecha_ini='+fecha_ini+'&fecha_fin='+fecha_fin,
        root        : 'serie',
        fields      : ['fecha_hora', 'v1','v2','v3','v4','v5','v6','di1','di2','di3','di4','di5','di6',
        'do1','do2','do3','do4','do5','do6','carga'],
        failure: function (form, action) {
            Ext.MessageBox.show({
                title   : 'Error...',
                msg     : 'No a ingresado correctamente vuelva a ingresar al sistema...',
                buttons : Ext.MessageBox.OK,
                icon    : Ext.MessageBox.ERROR
            });
        }
    });
    // Crear Grid
    var gccGrid = new Ext.grid.GridPanel({
        store: store,
        columns: [
        {
            id:'fecha_hora',
            header: '<center>Fecha Hora</center>',
            width: 120,
            sortable: true,
            dataIndex: 'fecha_hora'
        },{
            header: '<center>VAR 1</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v1'
        },{
            header: '<center>VAR 2</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v2'
        },{
            header: '<center>VAR 3</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v3'
        },{
            header: '<center>VAR 4</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v4'
        },{
            header: '<center>VAR 5</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v5'
        },{
            header: '<center>VAR 6</center>',
            width: 60,
            sortable: true,
            dataIndex: 'v6'
        },{
            header: '<center>IN 1</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di1'
        },{
            header: '<center>IN 2</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di2'
        },{
            header: '<center>IN 3</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di3'
        },{
            header: '<center>IN 4</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di4'
        },{
            header: '<center>IN 5</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di5'
        },{
            header: '<center>IN 6</center>',
            width: 60,
            sortable: true,
            dataIndex: 'di6'
        },{
            header: '<center>OUT 1</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do1'
        },{
            header: '<center>OUT 2</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do2'
        },{
            header: '<center>OUT 3</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do3'
        },{
            header: '<center>OUT 4</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do4'
        },{
            header: '<center>OUT 5</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do5'
        },{
            header: '<center>OUT 6</center>',
            width: 60,
            sortable: true,
            dataIndex: 'do6'
        },{
            header: '<center>CARGA</center>',
            width: 60,
            sortable: true,
            dataIndex: 'carga'
        }
        
        ],
        stripeRows: true,
        height: 532,
        title: '<center>'+'Reporte de Datos Hist\xF3ricos'+'</center>',
        stateful: true,
        stateId: 'grid',

        tbar: [
    //        '-',{
    //            xtype: 'box',
    //            autoEl: {
    //                tag: 'a',
    //                href: url,
    //                html: 'Exportar a Excel'
    //            }
    //        }
    ]
    });

    var tab = new Ext.Panel({
        title: 'Reporte de Datos Hist\xF3ricos' + '...',
        closable: true,
        iconCls: 'app-icon',
        items: gccGrid
    });

    start.add(tab);
}
