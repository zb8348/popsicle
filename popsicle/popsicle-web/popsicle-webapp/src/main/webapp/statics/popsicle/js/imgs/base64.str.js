var Base64 = {
		head:"",
		cs: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
		setHead:function(head){
			this.head=head;
		},
		encode416:function(src) {
		    src =this.head+src;
		    var buf = [];
		    src = src.replace(/([\da-f]{2})([\da-f]{2})([\da-f]{2})/ig, function($0, $1, $2, $3) {
		        $1 = parseInt($1, 16);
		        $2 = parseInt($2, 16);
		        $3 = parseInt($3, 16);
		        buf.push(Base64.cs.charAt(    $1 >> 2                ));
		        buf.push(Base64.cs.charAt(    (($1<<4)|($2>>4)) & 0x3f    ));
		        buf.push(Base64.cs.charAt(    (($2<<2)|($3>>6)) & 0x3f    ));
		        buf.push(Base64.cs.charAt(    $3 & 0x3f            ));
		        return "";
		    });
		    src = src.replace(/([\da-f]{2})(?:([\da-f]{2}))?/ig, function($0, $1, $2) {
		        $1 = parseInt($1, 16);
		        buf.push(Base64.cs.charAt( $1 >> 2 ));
		        if (typeof $2 == "undefined") {
		            buf.push(Base64.cs.charAt(    (($1<<4)|(0>>4))&0x3f    ));
		            buf.push("==");
		        } else {
		            $2 = parseInt($2, 16);
		            buf.push(Base64.cs.charAt(    (($1<<4)|($2>>4))&0x3f    ));
		            buf.push(Base64.cs.charAt(    (($2<<2)|(0>>6))&0x3f    ));
		            buf.push("=");
		        }
		    });
		    return buf.join("");
		},
		encode16ToBase64:function(head , src) {
		    src = head+src;
		    var buf = [];
		    src = src.replace(/([\da-f]{2})([\da-f]{2})([\da-f]{2})/ig, function($0, $1, $2, $3) {
		        $1 = parseInt($1, 16);
		        $2 = parseInt($2, 16);
		        $3 = parseInt($3, 16);
		        buf.push(Base64.cs.charAt(    $1 >> 2                ));
		        buf.push(Base64.cs.charAt(    (($1<<4)|($2>>4)) & 0x3f    ));
		        buf.push(Base64.cs.charAt(    (($2<<2)|($3>>6)) & 0x3f    ));
		        buf.push(Base64.cs.charAt(    $3 & 0x3f            ));
		        return "";
		    });
		    src = src.replace(/([\da-f]{2})(?:([\da-f]{2}))?/ig, function($0, $1, $2) {
		        $1 = parseInt($1, 16);
		        buf.push(Base64.cs.charAt( $1 >> 2 ));
		        if (typeof $2 == "undefined") {
		            buf.push(Base64.cs.charAt(    (($1<<4)|(0>>4))&0x3f    ));
		            buf.push("==");
		        } else {
		            $2 = parseInt($2, 16);
		            buf.push(Base64.cs.charAt(    (($1<<4)|($2>>4))&0x3f    ));
		            buf.push(Base64.cs.charAt(    (($2<<2)|(0>>6))&0x3f    ));
		            buf.push("=");
		        }
		    });
		    return buf.join("");
		}
}
