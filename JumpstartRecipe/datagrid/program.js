define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'URI ID',
    'Title',
    'Start Date/Time',
    'End Date/Time',
    'Last Modified'
  ]);
 
  return {
    type: 'program',
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
        label: t('Title'),
        field: 'titleBrief',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.localeAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('Start Date/Time'),
        field: 'startDateTime',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('End Date/Time'),
        field: 'endDateTime',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
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
        width: '100px'
      }
    ],
    sort: [
      { field: 'modifiedDate', direction: 'desc' },
      { field: 'uriId', direction: 'asc' }
    ]
  };
});
