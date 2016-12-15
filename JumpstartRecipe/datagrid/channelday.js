define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');


  TranslationResolver.fetch([
    'Channel',
    'Date'
  ]);

  return {
    type: 'channelDay',
    columns: [
      {
        label: t('ID'),
        field: 'uriId',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '150px'
      },
      {
        label: t('Date'),
        field: 'date',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Last Publish'),
        field: 'lastPublishDateTime',
        dateFormat: 'YYYY-MM-DD HH:mm:ss',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      }
    ],
    sort: [
      { field: 'date', direction: 'asc' }
    ]
  };
});
