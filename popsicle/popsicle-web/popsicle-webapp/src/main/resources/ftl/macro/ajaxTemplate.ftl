<#macro template title>
<#assign p=_P.genId()>
	<@f.htmlbox title>
		<#nested>
	</@f.htmlbox>
</#macro>



<#macro modal id title>
<!-- Modal -->
<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="${id}Label" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="${id}Label">${title}</h4>
            </div>
            <div class="modal-body" style="padding-left: 30px; padding-right: 30px;">
            	<#nested>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-primary" id="${id}Btn">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="${id}Close">关闭</button>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro bigModal id title>
<!-- Modal -->
<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="${id}Label" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="${id}Label">${title}</h4>
            </div>
            <div class="modal-body" style="padding-left: 30px; padding-right: 30px;">
            	<#nested>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-primary" id="${id}Btn">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro lgModal id title>
<!-- Modal -->
<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="${id}Label" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="${id}Label">${title}</h4>
            </div>
            <div class="modal-body">
        		<div id="${id}_modal_body"></div>
            </div>
        </div>
    </div>
</div>
</#macro>

