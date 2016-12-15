define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Source Path',
    'File Size',
    'Last Modified'
  ]);
 
  return {
    type: 'protectvideosub',
    columns: [
      {
        label: t('Source Path'),
        field: 'sourcePath',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('File Size'),
        field: 'contentFileSize',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '120px'
      },
      {
        label: t('Last Modified'),
        field: 'modifiedDate',
        sortable: true,
        filterable: true,
        width: '140px'
      }
    ],
    sort: [
      { field: 'masterSourceReference', direction: 'asc' },
      { field: 'sourcePath', direction: 'asc' }
    ]
  };
});
