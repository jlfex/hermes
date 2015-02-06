/**
 * JavaScript Suggest v0.1
 * Copyright (c) 2011 snandy
 * Blog: http://snandy.javaeye.com/
 * QQ群: 34580561
 * Date: 2011-10-14 
 * 
 * 
 * new InputSuggest({
 *       input         HTMLInputElement 必选
 *       data             Array ['sina.cn','sina.com','2008.sina.com','vip.sina.com.cn'] 必选
 *       containerCls    容器className
 *       itemCls        容器子项className
 *       activeCls        高亮子项className
 *       width         宽度设置 此项将覆盖containerCls的width
 *    opacity        透明度设置 此项将覆盖containerCls的opacity
 * });
 * 
 * 1, 使用getBoundingClientRect获取input位置，去掉else的判断
 * 
 */

function InputSuggest(opt){
    this.win = null;
    this.doc = null;    
    this.container = null;
    this.input = opt.input || null;
    this.containerCls = opt.containerCls || 'suggest-container';
    this.itemCls = opt.itemCls || 'suggest-item';
    this.activeCls = opt.activeCls || 'suggest-active';
    this.width = opt.width;
    this.opacity = opt.opacity;
    this.data = opt.data || [];
    this.active = null;
    this.visible = false;
    this.init();
}
InputSuggest.prototype = {
    init: function(){
        this.win = window;
        this.doc = window.document;
        this.container = this.$C('div');
        this.attr(this.container, 'class', this.containerCls);
        this.doc.body.appendChild(this.container);
        this.setPos();
        var me = this, input = this.input;

        this.on(input,'keyup',function(e){
            if(input.value==''){
                me.hide();
            }else{
                me.onKeyup(e);
            }
            
        });
        // blur会在click前发生，这里使用mousedown
        this.on(input,'blur',function(e){
            me.hide();
        });
        this.onMouseover();
        this.onMousedown();
        
    },
    $C: function(tag){
        return this.doc.createElement(tag);
    },
    getPos: function (el){
        var pos=[0,0];
        if(el.getBoundingClientRect) {
            var box = el.getBoundingClientRect();
            pos=[box.left,box.top];
        }
        return pos;
    },    
    setPos: function(){
        var input = this.input, 
            pos = this.getPos(input), 
            brow = this.brow, 
            width = this.width,
            opacity = this.opacity,
            container = this.container;
        container.style.cssText =
            'background:#F0F8FF;position:absolute;overflow:hidden;left:' 
            + pos[0] + 'px;top:'
            + (pos[1]+input.offsetHeight) + 'px;width:'
            // IE6/7/8/9/Chrome/Safari input[type=text] border默认为2，Firefox为1，因此取offsetWidth-2保证与FF一致
            + (brow.firefox ? input.clientWidth : input.offsetWidth-2) + 'px;';
        if(width){
            container.style.width = width + 'px';
        }
        if(opacity){
            if(this.brow.ie){
                container.style.filter = 'Alpha(Opacity=' + opacity * 100 + ');';
            }else{
                container.style.opacity = (opacity == 1 ? '' : '' + opacity);
            }
        }
    },
    show: function(){
        this.container.style.visibility = 'visible';
        this.visible = true;
    },
    hide: function(){
        this.container.style.visibility = 'hidden';
        this.visible = false;    
    },
    attr: function(el, name, val){
        if(val === undefined){
            return el.getAttribute(name);
        }else{
            el.setAttribute(name,val);
            name=='class' && (el.className = val);
        }
    },
    on: function(el, type, fn){
        el.addEventListener ? el.addEventListener(type, fn, false) : el.attachEvent('on' + type, fn);
    },
    un: function(el, type, fn){
        el.removeEventListener ? el.removeEventListener(type, fn, false) : el.detachEvent('on' + type, fn);
    },
    brow: function(ua){
        return {
            ie: /msie/.test(ua) && !/opera/.test(ua),
            opera: /opera/.test(ua),
            firefox: /firefox/.test(ua)
        };
    }(navigator.userAgent.toLowerCase()),
    onKeyup: function(e){
        var ary = [],
            input = this.input,
            iCls = this.itemCls,
            aCls = this.activeCls
            container = this.container;
            
        if(this.visible){
            switch(e.keyCode){
                case 13: // Enter
                    if(this.active){
                        input.value = this.active.firstChild.data;
                        this.hide();
                    }
                    return;
                case 38: // 方向键上
                    if(this.active==null){
                        this.active = container.lastChild;
                        this.attr(this.active, 'class', aCls);
                        input.value = this.active.firstChild.data;
                    }else{
                        if(this.active.previousSibling!=null){
                            this.attr(this.active, 'class', iCls);
                            this.active = this.active.previousSibling;
                            this.attr(this.active, 'class', aCls);
                            input.value = this.active.firstChild.data;
                        }else{
                            this.attr(this.active, 'class', iCls);
                            this.active = null;
                            input.focus();
                            input.value = input.getAttribute("curr_val");
                        }
                    }
                    return;
                case 40: // 方向键下
                    if(this.active==null){
                        this.active = container.firstChild;
                        this.attr(this.active, 'class', aCls);
                        input.value = this.active.firstChild.data;
                    }else{
                        if(this.active.nextSibling!=null){
                            this.attr(this.active, 'class', iCls);
                            this.active = this.active.nextSibling;
                            this.attr(this.active, 'class', aCls);
                            input.value = this.active.firstChild.data;
                        }else{
                            this.attr(this.active, 'class', iCls);
                            this.active = null;
                            input.focus();
                            input.value = input.getAttribute("curr_val");
                        }
                    }
                    return;
                    
            }
        
        }    
        if(e.keyCode==27){ // ESC键
            this.hide();
            input.value = this.attr(input,'curr_val');
            return;
        }
        
        if(this.attr(input,'curr_val')!=input.value) {
            this.container.innerHTML = '';
            var val = input.value,
                strs = [];
            if(input.value.indexOf('@')!=-1) {
                strs = input.value.split('@');
                ary.push(strs[1]);
                for(var i=0,len=this.data.length; i<len; i++) {
                    if(this.startsWith(this.data[i], strs[1])) {
                        ary.push(this.data[i]);
                    }
                }
            }
            ary = ary.length>=1 ? ary : this.data;
            for(var i=0; i<ary.length; i++) {
                this.createItem(strs[0]||val, ary[i]);
            }
            
            this.attr(input,'curr_val',input.value);
        }
        this.show();
    },
    startsWith: function(str, prefix) {
        return str.lastIndexOf(prefix, 0) == 0;
    },
    createItem: function(val, suffix) {
        suffix = suffix || '';
        var has = val.indexOf('@')!=-1;
        var item = this.$C('div');
        this.attr(item, 'class', this.itemCls);
        item.innerHTML = val + (has?'':'@') + suffix;
        this.container.appendChild(item);
    },
    onMouseover: function(){
        var me = this, icls = this.itemCls, acls = this.activeCls;
        this.on(this.container,'mouseover',function(e){
            var target = e.target || e.srcElement;
            if(target.className == icls){
                if(me.active){
                    me.active.className = icls;
                }
                target.className = acls;
                me.active = target;
            }
        });
    },
    onMousedown: function(){
        var me = this;
        this.on(this.container,'mousedown',function(e){
            var target = e.target || e.srcElement;
            me.input.value = target.innerHTML;
            me.hide();
        });
    }
};

