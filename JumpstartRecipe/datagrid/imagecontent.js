define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'URI ID',
    'Source URL',
    'Content Type',
    'File Size',
    'X-Res',
    'Y-Res',
    'Last Modified'
  ]);
 
  return {
    type: 'imagecontent',
    columns: [
      {
        label: t('URI ID'),
        field: 'uriId',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('Source URL'),
        field: 'sourceUrl',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
          label: t('Content Type'),
          field: 'contentType',
          renderer: function(context) {
        	  context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
        	  context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
        	  return context.value;
          },
          sortable: true,
          filterable: true,
          width: '100px'
        },
      {
        label: t('File Size'),
        field: 'fileSize',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('X-Res'),
        field: 'xResolution',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '60px'
      },
      {
        label: t('Y-Res'),
        field: 'yResolution',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '60px'
      },
      {
        label: t('Last Modified'),
        field: 'modifiedDate',
        sortable: true,
        filterable: true,
        width: '100px'
      }
    ],
    sort: [
      { field: 'modifiedDate', direction: 'desc' },
      { field: 'uriId', direction: 'asc' }
    ]
  };
});
