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
    'Duration',
    'Last Modified'
  ]);
 
  return {
    type: 'videocontent',
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
        label: t('Duration'),
        field: 'duration',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
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
