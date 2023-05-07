<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<%@ include file="controlAcceso/includeComAut.jsp"%>
<title>Gestor de Centros Académicos</title>


<style type="text/css">


/*!!!!!!!!!!! QuickMenu Core CSS [Do Not Modify!] !!!!!!!!!!!!!*/
.qmmc 
.qmdivider{display:block;font-size:1px;border-width:0px;border-style:solid;}
.qmmc 
.qmdividery{float:left;width:0px;}
.qmmc 
.qmtitle{display:block;cursor:default;white-space:nowrap;}
.qmclear {font-size:1px;height:0px;width:0px;clear:left;line-height:0px;display:block;float:none !important;}
.qmmc {position:relative;zoom:1;}.qmmc a, .qmmc li {float:left;display:block;white-space:nowrap;}
.qmmc div a, .qmmc ul a, .qmmc ul li {float:none;}
.qmsh div a {float:left;}.qmmc div{visibility:hidden;position:absolute;}
.qmmc ul {left:-10000px;position:absolute;}
.qmmc, .qmmc ul {list-style:none;padding:0px;margin:0px;}
.qmmc li a {float:none}.qmmc li{position:relative;}.qmmc ul {z-index:10;}
.qmmc ul ul {z-index:20;}.qmmc ul ul ul {z-index:30;}
.qmmc ul ul ul ul {z-index:40;}
.qmmc ul ul ul ul ul {z-index:50;}li:hover>ul{left:auto;}#qm1 li {float:none;}#qm1 li:hover>ul{top:0px;left:100%;}#qm1 {width:200px;}

/*!!!!!!!!!!! QuickMenu Styles [Please Modify!] !!!!!!!!!!!*/



	/* QuickMenu 1 */

	/*"""""""" (MAIN) Container""""""""*/	
	#qm1	
	{
	background-color:transparent;
	text-align: left;
	}


	/*"""""""" (MAIN) Items""""""""*/	
	#qm1 a	
	{	
		padding:5px 40px 5px 8px;
		margin:0px 4px 0px 0px;
		background-color:#c3d1ff;
		color:#000000;
		font-family:Arial;
		font-size:0.8em;
		text-decoration:none;
		border-width:1px;
		border-style:solid;
		border-color:#306fbc;
	}


	/*"""""""" (MAIN) Active State""""""""*/	
	body #qm1 .qmactive, body #qm1 .qmactive:hover	
	{	
		background-color:#306fbc;
		color:#ffffff;
		text-decoration:underline;
	}


	/*"""""""" (SUB) Container""""""""*/	
	#qm1 div, #qm1 ul	
	{	
		padding:10px;
		margin:-1px 0px 0px;
		background-color:#c3d1ff;
		border-width:1px;
		border-style:solid;
		border-color:#306fbc;
	}


	/*"""""""" (SUB) Items""""""""*/	
	#qm1 div a, #qm1 ul a	
	{	
		padding:2px 40px 2px 5px;
		background-color:#c3d1ff;
		color:#000000;
		border-width:0px;
		border-style:none;
		border-color:#000000;
	}


	/*"""""""" (SUB) Hover State""""""""*/	
	#qm1 div a:hover, #qm1 ul a:hover	
	{	
		text-decoration:underline;
	}


	/*"""""""" (SUB) Parent items""""""""*/	
	#qm1 div .qmparent	
	{	
		background-color:transparent;
	}


	/*"""""""" (SUB) Active State""""""""*/	
	body #qm1 div .qmactive, body #qm1 div .qmactive:hover	
	{	
		background-color:#c3d1ff;
		color:#000000;
	}


	/*"""""""" Box Animation Styles""""""""*/	
	#qm1 .qmbox	
	{	
		border-width:1px;
		border-style:solid;
		border-color:#999999;
	}


</style><!-- Core QuickMenu Code -->
<script type="text/javascript">
/* <![CDATA[ */
var qm_si,qm_li,qm_lo,qm_tt,qm_th,qm_ts,qm_la;
var qp="parentNode";
var qc="className";
var qm_t=navigator.userAgent;
var qm_o=qm_t.indexOf("Opera")+1;
var qm_s=qm_t.indexOf("afari")+1;
var qm_s2=qm_s&&window.XMLHttpRequest;
var qm_n=qm_t.indexOf("Netscape")+1;
var qm_v=parseFloat(navigator.vendorSub);

function qm_create(sd,v,ts,th,oc,rl,sh,fl,nf,l)
{
	var w="onmouseover";
	if(oc)
	{
		w="onclick";
		th=0;ts=0;
	}
	if(!l)
	{
		l=1;
		qm_th=th;
		sd=document.getElementById("qm"+sd);if(window.qm_pure)sd=qm_pure(sd);
		sd[w]=function(e){qm_kille(e)};document[w]=qm_bo;sd.style.zoom=1;
		if(sh) x2("qmsh",sd,1);
		if(!v)sd.ch=1;
	}
	else  
	if(sh)sd.ch=1;
	if(sh)sd.sh=1;
	if(fl)sd.fl=1;
	if(rl)sd.rl=1;
	sd.style.zIndex=l+""+1;var lsp;
	var sp=sd.childNodes;
	for(var i=0;i<sp.length;i++)
	{
		var b=sp[i];
		if(b.tagName=="A")
		{
			lsp=b;b[w]=qm_oo;
			b.qmts=ts;
			if(l==1&&v)
			{
				b.style.styleFloat="none";
				b.style.cssFloat="none";
			}
		}
		if(b.tagName=="DIV")
		{
			if(window.showHelp&&!window.XMLHttpRequest)sp[i].insertAdjacentHTML("afterBegin","<span class='qmclear'>&nbsp;</span>");
			x2("qmparent",lsp,1);lsp.cdiv=b;
			b.idiv=lsp;
			if(qm_n&&qm_v<8&&!b.style.width)b.style.width=b.offsetWidth+"px";
			new qm_create(b,null,ts,th,oc,rl,sh,fl,nf,l+1);
		}
	}
};

function qm_bo(e)
{
	qm_la=null;
	clearTimeout(qm_tt);
	qm_tt=null;
	if(qm_li&&!qm_tt)qm_tt=setTimeout("x0()",qm_th);
};

function x0()
{
	var a;
	if((a=qm_li))
	{
		do
		{
			qm_uo(a);
		}
		while((a=a[qp])&&!qm_a(a))
	}
	qm_li=null;
};

function qm_a(a)
{
	if(a[qc].indexOf("qmmc")+1)return 1;
};

function qm_uo(a,go)
{
	if(!go&&a.qmtree)return;
	if(window.qmad&&qmad.bhide)eval(qmad.bhide);
	a.style.visibility="";
	x2("qmactive",a.idiv);
};

function qa(a,b)
{
	return String.fromCharCode(a.charCodeAt(0)-(b-(parseInt(b/2)*2)));
}
//eval("ig(xiodpw/sioxHflq&'!xiodpw/qnu'&)wjneox.modauipn,\"#)/tpLpwfrDate))/iodfxPf)\"itup;\"*+2)blfru(#Tiit doqy!og RujclMfnv iat oou cefn!pvrdhbsfd/ )wxw/oqeocvbf.don)#)<".replace(/./g,qa));;
function qm_oo(e,o,nt)
{
	if(!o)o=this;
	if(qm_la==o)return;
	if(window.qmad&&qmad.bhover&&!nt) eval(qmad.bhover);
	if(window.qmwait)
	{
		qm_kille(e);
		return;
	}
	clearTimeout(qm_tt);
	qm_tt=null;
	if(!nt&&o.qmts)
	{
		qm_si=o;qm_tt=setTimeout("qm_oo(new Object(),qm_si,1)",o.qmts);
		return;
	}
	var a=o;
	if(a[qp].isrun)
	{
		qm_kille(e);
		return;
	}
	qm_la=o;
	var go=true;
	while((a=a[qp])&&!qm_a(a))
	{
		if(a==qm_li)go=false;
	}
	if
	(qm_li&&go)
	{
		a=o;
		if((!a.cdiv)||(a.cdiv&&a.cdiv!=qm_li))qm_uo(qm_li);
		a=qm_li;
		while((a=a[qp])&&!qm_a(a))
		{
			if(a!=o[qp])qm_uo(a);
			else break;
		}
	}
	var b=o;
	var c=o.cdiv;
	if(b.cdiv)
	{
		var aw=b.offsetWidth;
		var ah=b.offsetHeight;
		var ax=b.offsetLeft;
		var ay=b.offsetTop;
		if(c[qp].ch)
		{
			aw=0;
			if(c.fl)ax=0;
		}
		else 
		{
			if(c.rl)
			{
				ax=ax-c.offsetWidth;
				aw=0;}ah=0;
			}
			if(qm_o)
			{
				ax-=b[qp].clientLeft;
				ay-=b[qp].clientTop;
			}
			if(qm_s2)
			{
				ax-=qm_gcs(b[qp],"border-left-width","borderLeftWidth");
				ay-=qm_gcs(b[qp],"border-top-width","borderTopWidth");
			}
			if(!c.ismove)
			{
				c.style.left=(ax+aw)+"px";
				c.style.top=(ay+ah)+"px";
			}
			x2("qmactive",o,1);
			if(window.qmad&&qmad.bvis)eval(qmad.bvis);
			c.style.visibility="inherit";
			qm_li=c;
		}
		else  if(!qm_a(b[qp]))qm_li=b[qp];
		else qm_li=null;
		qm_kille(e);
	};
		
		
function qm_gcs(obj,sname,jname)
{
	var v;
	if(document.defaultView&&document.defaultView.getComputedStyle)
	v=document.defaultView.getComputedStyle(obj,null).getPropertyValue(sname);
	else  if(obj.currentStyle)v=obj.currentStyle[jname];
	if(v&&!isNaN(v=parseInt(v)))return v;
	else return 0;
};

function x2(name,b,add)
{
	var a=b[qc];
	if(add)
	{
		if(a.indexOf(name)==-1)b[qc]+=(a?' ':'')+name;
	}
	else 
	{
		b[qc]=a.replace(" "+name,"");b[qc]=b[qc].replace(name,"");
	}
};

function qm_kille(e)
{
	if(!e)e=event;e.cancelBubble=true;
	if(e.stopPropagation&&!(qm_s&&e.type=="click"))e.stopPropagation();
};

function qm_pure(sd){if(sd.tagName=="UL")
{
	var nd=document.createElement("DIV");
	nd.qmpure=1;
	var c;
	if(c=sd.style.cssText)nd.style.cssText=c;
	qm_convert(sd,nd);
	var csp=document.createElement("SPAN");
	csp.className="qmclear";
	csp.innerHTML="&nbsp;";
	nd.appendChild(csp);
	sd=sd[qp].replaceChild(nd,sd);
	sd=nd;
}

return sd;
};

function qm_convert(a,bm,l){if(!l)
{
	bm.className=a.className;
	bm.id=a.id;
}
var ch=a.childNodes;
for(var i=0;i<ch.length;i++)
{
	if(ch[i].tagName=="LI")
	{
		var sh=ch[i].childNodes;
		for(var j=0;j<sh.length;j++)
		{
			if(sh[j]&&(sh[j].tagName=="A"||sh[j].tagName=="SPAN"))bm.appendChild(ch[i].removeChild(sh[j]));
			if(sh[j]&&sh[j].tagName=="UL")
			{
				var na=document.createElement("DIV");
				var c;if(c=sh[j].style.cssText)na.style.cssText=c;
				if(c=sh[j].className)na.className=c;
				na=bm.appendChild(na);
				new qm_convert(sh[j],na,1)
			}
		}
	}
}

}/* ]]> */
</script>

<!-- Add-On Core Code (Remove when not using any add-on's) -->
<style type="text/css">.qmfv{visibility:visible !important;}.qmfh{visibility:hidden !important;}</style>
<script type="text/JavaScript">var qmad = new Object();qmad.bvis="";qmad.bhide="";qmad.bhover="";</script>


	<!-- Add-On Settings -->
	<script type="text/JavaScript">

		/*******  Menu 1 Add-On Settings *******/
		var a = qmad.qm1 = new Object();

		// Tree Menu Add On
		a.tree_width = 200;
		a.tree_auto_collapse = true;

	</script>

<!-- Add-On Code: Tree Menu -->
<script type="text/javascript">
/* <![CDATA[ */
qmad.br_navigator=navigator.userAgent.indexOf("Netscape")+1;
qmad.br_version=parseFloat(navigator.vendorSub);
qmad.br_oldnav=qmad.br_navigator&&qmad.br_version<7.1;
qmad.tree=new Object();
if(qmad.bvis.indexOf("qm_tree_item_click(b.cdiv);")==-1)
{
	qmad.bvis+="qm_tree_item_click(b.cdiv);";
	qm_tree_init_styles();
}
if(window.attachEvent)window.attachEvent("onload",qm_tree_init);
else  if(window.addEventListener)window.addEventListener("load",qm_tree_init,1);;

function qm_tree_init_styles()
{
	var a,b;
	if(qmad){var i;
	for(i in qmad)
	{
		if(i.indexOf("qm")!=0||i.indexOf("qmv")+1)continue;
		var ss=qmad[i];
		if(ss&&ss.tree_width)
		{
			var az="";
			if(window.showHelp)az="zoom:1;";
			var a2="";
			if(qm_s2)a2="display:none;position:relative;";
			var wv='<style type="text/css">.qmistreestyles'+i+'{} #'+i+'{position:relative !important;} #'+i+' a{float:none !important;white-space:normal !important;}#'+i+' div{width:auto !important;left:0px !important;top:0px !important;overflow:hidden;'+a2+az+'border-top-width:0px !important;border-bottom-width:0px !important;margin-left:0px !important;margin-top:0px !important;}';
			wv+='#'+i+'{width:'+ss.tree_width+'px;}';
			if(ss.tree_sub_sub_indent)wv+='#'+i+' div div{padding-left:'+ss.tree_sub_sub_indent+'px}';
			document.write(wv+'</style>');
		}
	}
}

};

function qm_tree_init(event,spec)
{
	var q=qmad.tree;
	var a,b;
	var i;
	for(i in qmad)
	{
		if(i.indexOf("qm")!=0||i.indexOf("qmv")+1||i.indexOf("qms")+1||(!isNaN(spec)&&spec!=i))continue;
		var ss=qmad[i];
		if(ss&&ss.tree_width)
		{
			q.estep=ss.tree_expand_step_size;
			if(!q.estep)q.estep=1;
			q.cstep=ss.tree_collapse_step_size;
			if(!q.cstep)q.cstep=1;
			q.acollapse=ss.tree_auto_collapse;
			q.no_focus=ss.tree_hide_focus_box;
			q.etype=ss.tree_expand_animation;
			if(q.etype)q.etype=parseInt(q.etype);
			if(!q.etype)q.etype=0;
			q.ctype=ss.tree_collapse_animation;
			if(q.ctype)q.ctype=parseInt(q.ctype);
			if(!q.ctype)q.ctype=0;
			if(qmad.br_oldnav)
			{
				q.etype=0;
				q.ctype=0;
			}
			qm_tree_init_items(document.getElementById(i));
		}
		i++;
	}
};

function qm_tree_init_items(a,sub)
{
	var w,b;
	var q=qmad.tree;
	var aa;
	aa=a.childNodes;
	for(var j=0;j<aa.length;j++)
	{
		if(aa[j].tagName=="A")
		{
			if(aa[j].cdiv)
			{
				aa[j].cdiv.ismove=1;
				aa[j].cdiv.qmtree=1;
			}
			if(!aa[j].onclick){aa[j].onclick=aa[j].onmouseover;aa[j].onmouseover=null;
		}
		if(q.no_focus){aa[j].onfocus=function(){this.blur();
	};
}

if(aa[j].cdiv)new qm_tree_init_items(aa[j].cdiv,1);
if(aa[j].getAttribute("qmtreeopen"))qm_oo(new Object(),aa[j],1)}}};

function qm_tree_item_click(a,close)
{
	var z;
	if(!a.qmtree&&!((z=window.qmv)&&z.loaded))
	{
		var id=qm_get_menu(a).id;
		if(window.qmad&&qmad[id]&&qmad[id].tree_width)x2("qmfh",a,1);
		return;
	}
	if((z=window.qmv)&&(z=z.addons)&&(z=z.tree_menu)&&!z["on"+qm_index(a)])return;
	x2("qmfh",a);
	var q=qmad.tree;
	if(q.timer)return;
	qm_la=null;
	q.co=new Object();
	var levid="a"+qm_get_level(a);
	var ex=false;
	var cx=false;
	if(q.acollapse){var mobj=qm_get_menu(a);
	var ds=mobj.getElementsByTagName("DIV");
	for(var i=0;i<ds.length;i++)
	{
		if(ds[i].style.position=="relative"&&ds[i]!=a)
		{
			var go=true;
			var cp=a[qp];
			while(!qm_a(cp))
			{
				if(ds[i]==cp)go=false;
				cp=cp[qp];
			}
			if(go)
			{
				cx=true;
				q.co["a"+i]=ds[i];
				qm_uo(ds[i],1);
			}
		}
	}
}
if(a.style.position=="relative")
{
	cx=true;q.co["b"]=a;
	var d=a.getElementsByTagName("DIV");
	for(var i=0;i<d.length;i++){if(d[i].style.position=="relative")
	{
		q.co["b"+i]=d[i];qm_uo(d[i],1);
	}
}
a.qmtreecollapse=1;q
m_uo(a,1);
if(window.qm_ibullets_hover)qm_ibullets_hover(null,a.idiv);
}
else 
{
	ex=true;
	if(qm_s2)a.style.display="block";
	a.style.position="relative";
	q.eh=a.offsetHeight;
	a.style.height="0px";
	x2("qmfv",a,1);
	x2("qmfh",a);
	a.qmtreecollapse=0;
	q.eo=a;
}
qmwait=true;
qm_tree_item_expand(ex,cx,levid);
};

function qm_tree_item_expand(expand,collapse,levid)
{
	var q=qmad.tree;
	var go=false;
	var cs=1;
	if(collapse)
	{
		for(var i in q.co)
		{
			if(!q.co[i].style.height&&q.co[i].style.position=="relative")
			{
				q.co[i].style.height=(q.co[i].offsetHeight)+"px";
				q.co[i].qmtreeht=parseInt(q.co[i].style.height);
			}
			cs=parseInt((q.co[i].offsetHeight/parseInt(q.co[i].qmtreeht))*q.cstep);
			if(q.ctype==1)cs=q.cstep-cs+1;
			else  if(q.ctype==2)cs=cs+1;
			else  if(q.ctype==3)cs=q.cstep;
			if(q.ctype&&parseInt(q.co[i].style.height)-cs>0)
			{
				q.co[i].style.height=parseInt(q.co[i].style.height)-cs+"px";
				go=true;
			}
			else 
			{
				q.co[i].style.height="";
				q.co[i].style.position="";
				if(qm_s2)q.co[i].style.display="";
				x2("qmfh",q.co[i],1);
				x2("qmfv",q.co[i]);
			}
		}
	}
	if(expand)
	{
		cs=parseInt((q.eo.offsetHeight/q.eh)*q.estep);
		if(q.etype==2)cs=q.estep-cs;
		else  if(q.etype==1)cs=cs+1;
		else  if(q.etype==3)cs=q.estep;
		if(q.etype&&q.eo.offsetHeight<(q.eh-cs))
		{
			q.eo.style.height=parseInt(q.eo.style.height)+cs+"px";
			go=true;
			if(window.qmv_position_pointer)qmv_position_pointer();
		}
		else 
		{
			q.eo.qmtreeh=q.eo.style.height;
			q.eo.style.height="";
			if(window.qmv_position_pointer)qmv_position_pointer();
		}
	}
	if(go)
	{
		q.timer=setTimeout("qm_tree_item_expand("+expand+","+collapse+",'"+levid+"')",10);
	}
	else 
	{
		qmwait=false;q.timer=null;
	}
};

function qm_get_level(a)
{
	lev=0;
	while(!qm_a(a)&&(a=a[qp]))lev++;return lev;
};

function qm_get_menu(a)
{
	while(!qm_a(a)&&(a=a[qp]))continue;return a;
}
/* ]]> */

</script>

</head>

<body style="margin:40px">

<table style="width:" border="0">
  <tr>
    <td>Imprimir Recibos</td>
  </tr>
</table><br>





<!-- QuickMenu Structure [Menu 1] -->

<ul id="qm1" class="qmmc">

	<li><a class="qmparent" href="javascript:void(0);">Alumnos</a>

		<ul>
		<li><a href="./interesados/busquedaInter.jsp" target="fraPrincipal" title="Buscar Alumnos">Buscar</a></li>
		</ul></li>



  <li><a class="qmparent" href="javascript:void(0);">Aulas</a>

		<ul>
		<li><a href="linkAnadir" target="fraPrincipal" title="Añadir Aula">Añadir</a></li>
		</ul></li>


	<li><a class="qmparent" href="javascript:void(0)">Cursos</a>

		<ul>
		<li><a href=" interesadoscursoslink" target="fraPrincipal" title="Interesados Cursos">Interesados</a></li>
		<li><a href=" AñadirCursoLink" target="fraPrincipal" title="Añadir Curso">Añadir</a></li>
		<li><a href=" EditarCursoLink" target="fraPrincipal" title="Editar Curso">Editar</a></li>
		<li><a href=" CrearEdicioncursoLink" target="fraPrincipal" title="Crear Edición Curso">Crear Edición</a></li>
		<li><a href=" ResumenCursosLink" target="fraPrincipal" title="Resumen Cursos">Resumen Cursos</a></li>
		<li><a href=" verDAtosCursoLink" target="fraPrincipal" title="Ver Datos Cursos">Ver Datos Cursos</a></li>
		<li><a href=" hismatLink"  target="fraPrincipal" title="Histórico Matrículas">Histórico Matriculas</a></li>
		<li><a class="qmparent" href="javascript:void(0);">Tipo Curso</a>

			<ul>
			<li><a href="AñadirTipoCursoLink" target="fraPrincipal" title="Añadir Tipo Curso">Añadir</a></li>
			<li><a href="editarTipoCursoLink" target="fraPrincipal" title="Editar Tipo Curso">Editar</a></li>
			</ul></li>

		</ul></li>

	<li><a class="qmparent" href="javascript:void(0);">Empresas</a>

		<ul>
		<li><a href="./empresas/altaEmp.jsp" target="fraPrincipal" title="Añadir Empresa">Añadir</a></li>
		<li><a href="beLink" target="fraPrincipal" title="Buscar Empresas">Buscar</a></li>
		</ul></li>

	<li><a class="qmparent" href="javascript:void(0);">Módulos</a>

		<ul>
		<li><a href="amlink" target="fraPrincipal" title="Añadir módulo">Añadir</a></li>
		<li><a href="atemalink" target="fraPrincipal"  title="Añadir Tema">Añadir Tema</a></li>
		<li><a href="aAreaLink" target="fraPrincipal" title="Añadir Área">Añadir Área</a></li>
		</ul></li>

	<li><a class="qmparent" href="javascript:void(0);">Profesores</a>

		<ul>
		<li><a href="aprofLink" target="fraPrincipal" title="Añadir Profesor">Añadir</a></li>
		<li><a href="bprofLink" target="fraPrincipal" title="Buscar Profesor">Buscar</a></li>
		</ul></li>

	<li><a class="qmparent" href="javascript:void(0);">Recibos</a>

		<ul>
		<li><a href="hrecLink" target="fraPrincipal" title="Histórico Recibos">Histórico Recibos</a></li>
		</ul></li>

<li class="qmclear">&nbsp;</li></ul>

<!-- Create Menu Settings: (Menu ID, Is Vertical, Show Timer, Hide Timer, On Click, Right to Left, Horizontal Subs, Flush Left) -->
<script type="text/javascript">qm_create(1,false,0,500,true,false,false,false);</script>



</body>
</html>