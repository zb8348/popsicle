<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/macro.ftl" as front>


<#macro html title>
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
	<http auto-config="false" disable-url-rewriting="true">
     <@front.head title=title>
     	<link rel='stylesheet' href='${statics}/statics/front/css/style.css' />
        <style type='text/css'>
            body {
                padding-top: 50px;
                padding-bottom: 40px;
                color: #5a5a5a;
            }
            /* 轮播广告 */

            .carousel {
                height: 500px;
                margin-bottom: 60px;
            }

            .carousel .item {
                height: 500px;
                background-color: #009933;
            }

            .carousel .item img {
                width: 100%;
            }

            .carousel-caption {
                z-index: 10;
            }

            .carousel-caption p {
                margin-bottom: 20px;
                font-size: 20px;
                line-height: 1.8;
            }
            /* 简介 */

            .summary {
                padding-right: 15px;
                padding-left: 15px;
            }

            .summary .col-md-4 {
                margin-bottom: 20px;
                text-align: center;
            }
            /* 特性 */

            .feature-divider {
                margin: 40px 0;
            }

            .feature {
                padding: 30px 0;
            }

            .feature-heading {
                font-size: 50px;
                color: #2a6496;
            }

            .feature-heading .text-muted {
                font-size: 28px;
            }
            /* 响应式布局 */

            @media (max-width: 768px) {
                .summary {
                    padding-right: 3px;
                    padding-left: 3px;
                }
                .carousel {
                    height: 300px;
                    margin-bottom: 30px;
                }
                .carousel .item {
                    height: 300px;
                }
                .carousel img {
                    min-height: 300px;
                }
                .carousel-caption p {
                    font-size: 16px;
                    line-height: 1.4;
                }
                .feature-heading {
                    font-size: 34px;
                }
                .feature-heading .text-muted {
                    font-size: 22px;
                }
            }

            @media (min-width: 992px) {
                .feature-heading {
                    margin-top: 120px;
                }
            }
        </style>
     </@front.head>


<script>
	function selectMen(parentClass,childrenClass){
		$('#pop_nav li').removeClass("active");
		if(parentClass){
			$('#'+parentClass).addClass("active");
		}
		if(childrenClass){
			$('#'+childrenClass).addClass("active");
		}
	}
</script>

<body>
	<@front.navbar/>
    
	<#nested>

    <@front.foot/>
    
    <@front.aboutme/>
	
<!--
<script>
    $(function() {
        $('#ad-carousel').carousel();
        $('#menu-nav .navbar-collapse a').click(function(e) {
            var href = $(this).attr('href');
            var tabId = $(this).attr('data-tab');
            if ('#' !== href) {
                e.preventDefault();
                $(document).scrollTop($(href).offset().top - 70);
                if (tabId) {
                    $('#feature-tab a[href=#' + tabId + ']').tab('show');
                }
            }
        });
    });
</script>
-->
</body>
</html>
</#macro>

