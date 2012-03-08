/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var iconVerde = "<img src='./img/lv.png' alt='activo'>";
var iconRojo = "<img src='./img/lr.png' alt='inactivo'>";
var id_est;

function agregarTab(tipoReporte,est){
    id_est = est;
    var main = new Ext.Panel({
        id: 'table-panel',
        
        layout: {
            type: 'table',
            columns: 2
        },
        defaults: {
            bodyStyle: 'padding:15px 15px'
        },
        items: [{
            html: 'Cargando indicadores digitales...',
            id  : 'leds',
            colspan: 2
        },
        crearGrafica(1), crearGrafica(2), 
        crearGrafica(3),crearGrafica(4), 
        crearGrafica(5),crearGrafica(6)
        //        ,{
        //            title: 'More Column Spanning',
        //            html: '<p>Spanning three columns.</p>',
        //            colspan: 2
        //        }
        ]
    });

    var tab = new Ext.Panel({
        title       : tipoReporte + '...',
        closable    : true, //<-- este tab se puede cerrar
        iconCls     : 'app-icon',
        autoScroll  : true,
        id          : 'tab',
        items       : main
    });

    start.add(tab);
    start.setActiveTab(tab);
    actualizarGraficas();
}

function actualizarGraficas(){
    var tb = Ext.get('tab');
    if(tb!= undefined){
        if(tb.hidden!=false){
            actualizarStoresGraficas(1);
            actualizarStoresGraficas(2);
            actualizarStoresGraficas(3);
            actualizarStoresGraficas(4);
            actualizarStoresGraficas(5);
            actualizarStoresGraficas(6);
        }
    }
    // Volvemos a comprobar datos a los 5 segundos
    setTimeout( function(){
        actualizarGraficas();
    }
    , 1*1000);
}

function actualizarStoresGraficas(id_var){
    var store = Ext.StoreMgr.lookup('store'+id_var);
    store.proxy.conn.url = './php/monitoreo/graficas/getMediciones.php?id_var='+id_var+'&id_est='+id_est;
    store.load();
    $.ajax({
        url : 'php/monitoreo/getUltimosDatos.php?id_est='+id_est,
        type : 'GET',
        async : true,
        success : function( datos ){
            var obj = eval('(' + datos + ')');
            
            setValorUltimoDato(1,obj.var1);
            setValorUltimoDato(2,obj.var2);
            setValorUltimoDato(3,obj.var3);
            setValorUltimoDato(4,obj.var4);
            setValorUltimoDato(5,obj.var5);
            setValorUltimoDato(6,obj.var6);
            
            var leds = Ext.get('leds');
            leds.dom.innerHTML = tablaLedsEntradasSalidas(obj);
        }
    });
}

/**
 * Pone el valor del ultimo dato en las cajas de texto debajo de las graficas
 */
function setValorUltimoDato(id,valor){
    var txt = Ext.get('txt'+id);
    txt.dom.value = valor;
}

/**
 * Crea todo el componente de la grafica para que sea mostrada 
 */
function crearGrafica(id_var){
    var store = new Ext.data.JsonStore({
        autoDestroy : true,
        autoLoad    : true,
        storeId          : 'store'+id_var,
        name        : 'str'+id_var,
        url         : './php/monitoreo/graficas/getMediciones.php?id_var='+id_var,
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
		
    var lineChart1 = new Ext.chart.LineChart({
        store: store,
        xField: 'time',
        yField: 'value'
    });
		
    var panel1 = new Ext.Panel({
        title : 'Gr\xE1fica '+ id_var,
        width : 500,
        height: 250,
        items:[
        lineChart1
        ]
    });
    
    var grafica = new Ext.form.FormPanel({
        defaults: {
            bodyStyle: 'padding:15px 15px'
        },
        items:[
        panel1,
        { 
            xtype: 'textfield',
            disable: true,
            fieldLabel: 'Medici\xF3n Actual',
            name: 'txtValorGrafica'+ id_var,
            id  : 'txt'+id_var
        }
        ]
    });
    
    return grafica;
}

function tablaLedsEntradasSalidas(obj){
    var tabla = "<table border='0' ALIGN='center'> \n\
                <tr> \n\
                    <td colspan='6' ALIGN='center'><h1>Entradas</h1></td> \n\
                    <td width='50'></td> \n\
                    <td colspan='6' ALIGN='center'><h1>Salidas</h1></td> \n\
                </tr> \n\
                <tr> \n\
                    <td>Entrada 1</td> \n\
                    <td>Entrada 2</td> \n\
                    <td>Entrada 3</td> \n\
                    <td>Entrada 4</td> \n\
                    <td>Entrada 5</td> \n\
                    <td>Entrada 6</td> \n\
                    <td width='50'></td> \n\
                    <td>Salida 1</td> \n\
                    <td>Salida 2</td> \n\
                    <td>Salida 3</td> \n\
                    <td>Salida 4</td> \n\
                    <td>Salida 5</td> \n\
                    <td>Salida 6</td> \n\
                </tr> \n\
                <tr class='.centroTabla'> \n\
                    <td>"+getIconEntrada1(obj)+"</td> \n\
                    <td>"+getIconEntrada2(obj)+"</td> \n\
                    <td>"+getIconEntrada3(obj)+"</td> \n\
                    <td>"+getIconEntrada4(obj)+"</td> \n\
                    <td>"+getIconEntrada5(obj)+"</td> \n\
                    <td>"+getIconEntrada6(obj)+"</td> \n\
                    <td width='50'></td> \n\
                    <td>"+getIconSalida1(obj)+"</td> \n\
                    <td>"+getIconSalida2(obj)+"</td> \n\
                    <td>"+getIconSalida3(obj)+"</td> \n\
                    <td>"+getIconSalida4(obj)+"</td> \n\
                    <td>"+getIconSalida5(obj)+"</td> \n\
                    <td>"+getIconSalida6(obj)+"</td> \n\
                </tr> \n\
                </table>";
    return tabla;
}

function getIconEntrada1(obj){

    if(obj.din1==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconEntrada2(obj){
    if(obj.din2==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconEntrada3(obj){
    if(obj.din3==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconEntrada4(obj){
    if(obj.din4==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconEntrada5(obj){
    if(obj.din5==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconEntrada6(obj){
    if(obj.din6==1){
        return iconVerde;
    }else{
        return iconRojo;
    }
}
function getIconSalida1(obj){
    if(obj.dou1==1){
        return "<img id='out1' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out1' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}
function getIconSalida2(obj){
    if(obj.dou2==1){
        return "<img id='out2' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out2' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}
function getIconSalida3(obj){
    if(obj.dou3==1){
        return "<img id='out3' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out3' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}
function getIconSalida4(obj){
    if(obj.dou4==1){
        return "<img id='out4' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out4' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}
function getIconSalida5(obj){
    if(obj.dou5==1){
        return "<img id='out5' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out5' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}
function getIconSalida6(obj){
    if(obj.dou6==1){
        return "<img id='out6' src='./img/lv.png' alt='activo' onClick='changeImage(id);'>";
    }else{
        return "<img id='out6' src='./img/lr.png' alt='inactivo' onClick='changeImage(id);'>";
    }
}

function changeImage(id){
    var img = Ext.get(id);
    var ac;
    if(img.dom.alt=="activo"){
        ac=0;
    }else{
        ac=1;
    }
  
    $.ajax({
        url : 'php/monitoreo/setSalidaUltimosDatos.php',
        type : 'GET',
        async : true,
        data: "ac="+ac+"&id_var="+id.substr(3)+"&id_est="+id_est,
        success : function( datos ){
            var r = eval('(' + datos + ')');
            if(r.ac==0){
                img.dom.src = './img/lr.png';
            }else{
                img.dom.src = './img/lv.png';
            }
            
        }
    });    
}
