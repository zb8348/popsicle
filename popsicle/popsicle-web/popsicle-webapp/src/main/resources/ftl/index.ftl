<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="YD">
<!-- TradingView Widget BEGIN 
<script type="text/javascript" src="${statics}/statics/front/js/tradingview/tv.js"></script>
<script type="text/javascript">
new TradingView.widget({
  "width":960,
  "height":430,
  //"autosize": true,
  "symbol": "FX:SPX500",
  "interval": "1",
  "timezone": "Asia/Shanghai",
  "theme": "Grey",
  "style": "9",
  "locale": "zh",
  "toolbar_bg": "rgba(241, 243, 246, 1)",
  "withdateranges": true,
  "hide_side_toolbar": false,
  "allow_symbol_change": true,
  "hideideas": true
});
</script>
TradingView Widget END -->

<!-- TradingView Widget BEGIN 
<div id="tv-miniwidget-de43a"></div>
<script type="text/javascript" src="${statics}/statics/front/js/tradingview/tv.js"></script>
<script type="text/javascript">
new TradingView.MiniWidget({
  "container_id": "tv-miniwidget-de43a",
  "tabs": [
    "Forex"
  ],
  "symbols": {
    "Forex": [
      "FX:EURUSD",
      "FX:GBPUSD",
      "FX:USDJPY",
      "FX:USDCHF",
      "FX:AUDUSD",
      "FX:USDCAD"
    ]
  },
  "gridLineColor": "#E9E9EA",
  "fontColor": "#83888D",
  "underLineColor": "#dbeffb",
  "trendLineColor": "#4bafe9",
  "activeTickerBackgroundColor": "#EDF0F3",
  "large_chart_url": "https://www.tradingview.com/chart/",
  "noGraph": false,
  "width": "300px",
  "height": "400px",
  "locale": "zh"
});
</script>
TradingView Widget END -->


		<!-- 广告轮播 -->
		<div id="ad-carousel" class="carousel slide" data-ride="carousel">
		    <ol class="carousel-indicators">
		        <li data-target="#ad-carousel" data-slide-to="0" class="active"></li>
		        <li data-target="#ad-carousel" data-slide-to="1"></li>
		    </ol>
		    <div class="carousel-inner">
		        <div class="item active">
		            <img width="100%" height="100%" src="${statics}/statics/front/images/1.jpg" alt="1 slide">
		            <div class="container">
		                <div class="carousel-caption">
		                    <h1>Chrome</h1>
		                    <p>Google Chrome，又称Google浏览器，是一个由Google（谷歌）公司开发的网页浏览器。</p>
		                    <p><a class="btn btn-lg btn-primary" href="http://www.google.cn/intl/zh-CN/chrome/browser/" role="button" target="_blank">点我下载</a></p>
		                </div>
		            </div>
		        </div>
		        <div class="item">
		            <img src="${statics}/statics/front/images/double.jpg" alt="2 slide">
		            <div class="container">
		                <div class="carousel-caption">
		                    <h1>Firefox</h1>
		                    <p>Mozilla Firefox，中文名通常称为“火狐”或“火狐浏览器”，是一个开源网页浏览器。</p>
		                    <p><a class="btn btn-lg btn-primary" href="http://www.firefox.com.cn/download/" target="_blank" role="button">点我下载</a></p>
		                </div>
		            </div>
		        </div>
		        <a class="left carousel-control" href="#ad-carousel" data-slide="prev"></a>
		        <a class="right carousel-control" href="#ad-carousel" data-slide="next"></a>
		    </div>
		</div>

</@template.html>